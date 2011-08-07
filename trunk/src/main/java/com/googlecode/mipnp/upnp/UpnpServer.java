/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010, 2011  Jochem Van denbussche
 *
 * This file is part of MiPnP.
 *
 * MiPnP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MiPnP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * UpnpServer.java
 * Created on Jul 10, 2011, 11:07:54 AM
 */
package com.googlecode.mipnp.upnp;

import com.googlecode.mipnp.upnp.discovery.DiscoveryServer;
import com.googlecode.mipnp.upnp.description.DeviceDescriptionServlet;
import com.googlecode.mipnp.upnp.description.ServiceDescriptionServlet;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.EnumSet;
import javax.servlet.Servlet;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.eclipse.jetty.server.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class UpnpServer {

    private static final String DEVICE_DESCRIPTION = "/description.xml";
    private static final String CONTROL = "/control";

    private RootDevice rootDevice;
    private InetAddress bindAddr;

    private Server httpServer;
    private ServletContextHandler context;
    private DiscoveryServer advertiser;

    public UpnpServer(
            RootDevice rootDevice,
            InetAddress bindAddr)
            throws SocketException {

        this.rootDevice = rootDevice;
        this.bindAddr = bindAddr;

        initHttpServer();
        initAdvertiser();
    }

    public void start() throws Exception {
        httpServer.start();

        for (Service service : rootDevice.getServices()) {
            URI fullServiceControlUri =
                    new URI(CONTROL + "/" + service.getId().toLowerCase());
            URI partialServiceControlUri =
                    new URI("/" + service.getId().toLowerCase());
            service.setControlUri(fullServiceControlUri);
            Endpoint.publish(partialServiceControlUri.toASCIIString(), service);
        }

        URL deviceDescUrl = new URL(
                "http",
                bindAddr.getHostAddress(),
                getHttpPort(),
                DEVICE_DESCRIPTION);

        rootDevice.setDescriptionUrl(deviceDescUrl);

        advertiser.start();
    }

    public void stop() throws Exception {
        advertiser.stop();
        httpServer.stop();
    }

    public void join() throws InterruptedException {
        httpServer.join();
    }

    public InetAddress getHttpAddress() {
        return bindAddr;
    }

    public int getHttpPort() {
        return httpServer.getConnectors()[0].getLocalPort();
    }

    public void addServlet(Servlet servlet, String path) {
        context.addServlet(new ServletHolder(servlet), path);
    }

    private void initHttpServer() {
        InetSocketAddress httpBindAddr = new InetSocketAddress(bindAddr, 0);
        this.httpServer = new Server(httpBindAddr);
        context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
//        EnumSet<DispatcherType> set = EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR);
        EnumSet<DispatcherType> set = EnumSet.allOf(DispatcherType.class);
        context.addFilter(
                new FilterHolder(new ServerHeaderFilter(rootDevice)), "/*", set);
        httpServer.setHandler(context);

        Servlet descriptionServlet = new DeviceDescriptionServlet(rootDevice);
        context.addServlet(
                new ServletHolder(descriptionServlet),
                DEVICE_DESCRIPTION);

        for (Service service : rootDevice.getServices()) {
            URI serviceDescUri = null;
            try {
                serviceDescUri =
                        new URI("/" + service.getId().toLowerCase() + ".xml");
            } catch (URISyntaxException ex) {
                // TODO: check if this can happen
                ex.printStackTrace();
            }
            service.setDescriptionUri(serviceDescUri);
            context.addServlet(new ServletHolder(
                    new ServiceDescriptionServlet(rootDevice, service)),
                    serviceDescUri.toASCIIString());
        }

        // TODO: ServiceDescriptionServlet for services from embedded devices

        CXFNonSpringServlet cxf = new CXFNonSpringServlet();
        ServletHolder servletHolder = new ServletHolder(cxf);
//        servletHolder.setName("soap");
//        servletHolder.setForcedPath("soap");
//        context.addServlet(servletHolder, "/*");
        servletHolder.setName("action");
        servletHolder.setForcedPath("action");
        context.addServlet(servletHolder, CONTROL + "/*");

        Bus bus = cxf.getBus();
        BusFactory.setDefaultBus(bus);
    }

    private void initAdvertiser() throws SocketException {
        NetworkInterface ni = NetworkInterface.getByInetAddress(bindAddr);
        System.out.println("NetworkInterface: " + ni.getName());
        this.advertiser = new DiscoveryServer(rootDevice, bindAddr, ni);
    }
}
