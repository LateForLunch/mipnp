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
 * SsdpServer.java
 * Created on Dec 4, 2010, 4:18:13 PM
 */
package domain.ssdp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SsdpServer implements SsdpConstants {

    private String groupAddress;
    private InetAddress group;
    private int port;
    private MulticastSocket multicastSocket;
    private int ttl;
    private SsdpServerMainThread serverMain;
    private Thread serverThread;
    private List<ISsdpRequestHandler> handlers;

    public SsdpServer() {
        this(SSDP_DEFAULT_ADDRESS, SSDP_DEFAULT_PORT, SSDP_DEFAULT_TTL);
    }

    public SsdpServer(String groupAddress, int port, int ttl) {
        setGroupAddress(groupAddress);
        setPort(port);
        this.handlers = new ArrayList<ISsdpRequestHandler>();
    }

    public void start() throws IOException {
        this.group = InetAddress.getByName(groupAddress);
        this.multicastSocket = new MulticastSocket(port);
        multicastSocket.setTimeToLive(ttl);
        multicastSocket.joinGroup(group);
        this.serverMain = new SsdpServerMainThread(this, multicastSocket);
        this.serverThread = new Thread(serverMain);
        serverThread.setName("SsdpServerMain");
        serverThread.start();
    }

    public void stop() {
        try {
            multicastSocket.leaveGroup(group);
        } catch (IOException ex) {
            ex.printStackTrace(); // TODO
        }
        multicastSocket.close();
        this.multicastSocket = null;
        this.group = null;
        this.serverThread = null;
        this.serverMain = null;
    }

    public void addRequestHandler(ISsdpRequestHandler handler) {
        handlers.add(handler);
    }

    public void removeRequestHandler(ISsdpRequestHandler handler) {
        handlers.remove(handler);
    }

    protected void notifyHandlers(SsdpRequest request) {
        for (ISsdpRequestHandler handler : handlers) {
            handler.handleSsdpRequest(request);
        }
    }

    public String getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    public int getPort() {
        if (multicastSocket != null) {
            return multicastSocket.getLocalPort();
        }
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeToLive() {
        if (multicastSocket != null) {
            try {
                return multicastSocket.getTimeToLive();
            } catch (IOException ex) {
                return ttl;
            }
        }
        return ttl;
    }

    public void setTimeToLive(int ttl) throws IOException {
        if (multicastSocket != null) {
            multicastSocket.setTimeToLive(ttl);
        }
        this.ttl = ttl;
    }

    // TEST
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press 'q' to stop.\nCreating SSDP server...");
        SsdpServer server = new SsdpServer();
        ISsdpRequestHandler handler = new ISsdpRequestHandler() {

            public void handleSsdpRequest(SsdpRequest request) {
                System.out.println(request.getMethod() + " " + request.getRequestUri());
            }
        };
        server.addRequestHandler(handler);
        try {
            server.start();
        } catch (IOException ex) {
            System.out.println("FAILED");
            ex.printStackTrace();
            System.exit(1);
        }
        System.out.println("OK");
        while (!(scanner.nextLine().equalsIgnoreCase("q"))) {
            System.out.println("Unknown command.\nPress 'q' to stop.\n");
        }
        server.stop();
    }
}
