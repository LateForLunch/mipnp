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
package com.googlecode.mipnp;

import com.googlecode.mipnp.test.TestServlet;
import com.googlecode.mipnp.test.TimeServerImpl;
import com.googlecode.mipnp.upnp.IRootDevice;
import com.googlecode.mipnp.upnp.description.DescriptionServlet;
import com.googlecode.mipnp.upnp.mediaserver.MediaServer;
import java.util.Scanner;
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
public class App {

    public static void main(String[] args) {
        String busFactory =
                System.getProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME);
        System.setProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME,
                "org.apache.cxf.bus.CXFBusFactory");

        Server server = null;

        try {
            IRootDevice rootDevice = new MediaServer();

            server = new Server(8080);
            server.setSendServerVersion(false);
            ServletContextHandler context =
                    new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);

            Servlet descriptionServlet = new DescriptionServlet(rootDevice);
            context.addServlet(
                    new ServletHolder(descriptionServlet), "/description.xml");

            Servlet testServlet = new TestServlet();
            context.addServlet(new ServletHolder(testServlet), "/test");

            CXFNonSpringServlet cxf = new CXFNonSpringServlet();
            ServletHolder servletHolder = new ServletHolder(cxf);
            servletHolder.setName("soap");
            servletHolder.setForcedPath("soap");
            context.addServlet(servletHolder, "/soap/*");

            server.start();

            Bus bus = cxf.getBus();
            BusFactory.setDefaultBus(bus);

            TimeServerImpl ts = new TimeServerImpl();
            Endpoint.publish("/ts", ts);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Press 'q' to stop.");
            while (!(scanner.nextLine().equalsIgnoreCase("q"))) {
                System.out.println("Unknown command.\nPress 'q' to stop.\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.stop();
                    server.join();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (busFactory != null) {
                System.setProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME,
                        busFactory);
            } else {
                System.clearProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME);
            }
        }
    }
}
