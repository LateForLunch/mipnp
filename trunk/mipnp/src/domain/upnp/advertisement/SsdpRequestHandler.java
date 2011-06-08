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
 * SsdpRequestHandler.java
 * Created on Dec 5, 2010, 10:55:40 AM
 */
package domain.upnp.advertisement;

import cli.Settings;
import domain.ssdp.ISsdpRequestHandler;
import domain.ssdp.SsdpRequest;
import domain.ssdp.SsdpMulticastServer;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Scanner;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SsdpRequestHandler implements ISsdpRequestHandler {

    public SsdpRequestHandler() {
    }

    public void handleSsdpRequest(SsdpRequest request) {
        if (!request.isMsearch()) {
            return;
        }
        // TODO: ask advertiser to do his job
    }

    // TEST
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press 'q' to stop.\nCreating SSDP server...");
        SsdpMulticastServer ssdpServer = null;
        try {
            ssdpServer = new SsdpMulticastServer(
                    NetworkInterface.getByName("eth0"));
        } catch (IOException ex) {
            System.out.println("FAILED");
            ex.printStackTrace();
            System.exit(1);
        }
        SsdpRequestHandler handler = new SsdpRequestHandler();
        ssdpServer.addRequestHandler(handler);
        try {
            ssdpServer.start();
        } catch (IOException ex) {
            System.out.println("FAILED");
            ex.printStackTrace();
            System.exit(1);
        }
        System.out.println("OK");
        while (!(scanner.nextLine().equalsIgnoreCase("q"))) {
            System.out.println("Unknown command.\nPress 'q' to stop.\n");
        }
        ssdpServer.stop();
    }
}
