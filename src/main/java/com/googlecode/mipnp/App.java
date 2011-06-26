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

import com.googlecode.mipnp.upnp.description.DescriptionServlet;
import com.googlecode.mipnp.upnp.mediaserver.MediaServer;
import java.util.Scanner;
import org.eclipse.jetty.server.Server;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class App {

    public static void main(String[] args) {
        Server server = new Server(8080);
        server.setHandler(new DescriptionServlet(new MediaServer()));
        try {
            server.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 'q' to stop.");
        while (!(scanner.nextLine().equalsIgnoreCase("q"))) {
            System.out.println("Unknown command.\nPress 'q' to stop.\n");
        }
        try {
            server.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
