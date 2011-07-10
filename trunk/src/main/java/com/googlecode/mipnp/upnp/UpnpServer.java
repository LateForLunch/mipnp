/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Server.java
 * Created on Jul 10, 2011, 11:07:54 AM
 */
package com.googlecode.mipnp.upnp;

import com.googlecode.mipnp.test.TimeServerImpl;
import com.googlecode.mipnp.upnp.description.DescriptionServlet;
import javax.servlet.Servlet;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class UpnpServer {

    private RootDevice rootDevice;
    private Server server;

    public UpnpServer(RootDevice rootDevice, int httpPort) {
        this.rootDevice = rootDevice;
        this.server = new Server(httpPort);
        server.setSendServerVersion(false);
        ServletContextHandler context =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        Servlet descriptionServlet = new DescriptionServlet(rootDevice);
        context.addServlet(
                new ServletHolder(descriptionServlet), "/description.xml");

        CXFNonSpringServlet cxf = new CXFNonSpringServlet();
        ServletHolder servletHolder = new ServletHolder(cxf);
        servletHolder.setName("soap");
        servletHolder.setForcedPath("soap");
        context.addServlet(servletHolder, "/soap/*");

        Bus bus = cxf.getBus();
        BusFactory.setDefaultBus(bus);
    }

    public void start() throws Exception {
        server.start();

        // TODO: publish web services
        TimeServerImpl ts = new TimeServerImpl();
        Endpoint.publish("/ts", ts);
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void join() throws InterruptedException {
        server.join();
    }
}
