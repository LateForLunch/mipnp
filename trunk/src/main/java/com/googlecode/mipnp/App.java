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

import com.googlecode.mipnp.upnp.RootDevice;
import com.googlecode.mipnp.upnp.UpnpServer;
import com.googlecode.mipnp.impl.mediaserver.MediaServer;
import java.util.Scanner;
import org.apache.cxf.BusFactory;

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

        UpnpServer server = null;
        try {
            RootDevice rootDevice = new MediaServer();
            server = new UpnpServer(rootDevice, 8080);

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
