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
 * SsdpMulticastServer.java
 * Created on Dec 4, 2010, 4:18:13 PM
 */
package domain.ssdp;

import cli.Settings;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class creates a new Thread to listen to multicast SSDP messages.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SsdpMulticastServer implements SsdpConstants {

    private InetAddress group;
    private int port;
    private SocketAddress socketAddress;
    private NetworkInterface nic;
    private MulticastSocket multicastSocket;
    private int ttl;
    private SsdpMulticastServerMainThread serverMain;
    private Thread serverThread;
    private List<ISsdpRequestHandler> handlers;

    public SsdpMulticastServer(NetworkInterface nic) throws UnknownHostException, IOException {
        this(SSDP_DEFAULT_ADDRESS, SSDP_DEFAULT_PORT, nic, SSDP_DEFAULT_TTL);
    }

    public SsdpMulticastServer(
            String groupAddress, int port, NetworkInterface nic, int ttl)
            throws UnknownHostException {

        this.port = port;
        this.ttl = ttl;
        this.group = InetAddress.getByName(groupAddress);
        this.socketAddress = new InetSocketAddress(group, port);
        this.nic = nic;
        this.handlers = new ArrayList<ISsdpRequestHandler>();
    }

    /**
     * Start to listen to multicast SSDP messages.
     * @throws IOException if an I/O error occurs
     */
    public void start() throws IOException {
        this.multicastSocket = new MulticastSocket(port);
        multicastSocket.setTimeToLive(ttl);
        multicastSocket.joinGroup(socketAddress, nic);
        this.serverMain = new SsdpMulticastServerMainThread(
                this, multicastSocket);
        this.serverThread = new Thread(serverMain);
        serverThread.setName("SsdpServerMain");
        serverThread.start();
    }

    /**
     * Stop listening for multicast SSDP messages.
     */
    public void stop() {
        try {
            multicastSocket.leaveGroup(socketAddress, nic);
        } catch (IOException ex) {
            ex.printStackTrace(); // TODO
        }
        multicastSocket.close();
        this.multicastSocket = null;
        this.serverThread = null;
        this.serverMain = null;
    }

    /**
     * Add a ISsdpRequestHandler.<br />
     * The ISsdpRequestHandler will be calles when a new SsdpRequest comes in.
     * @param handler the handler to add
     */
    public void addRequestHandler(ISsdpRequestHandler handler) {
        handlers.add(handler);
    }

    /**
     * Remove a ISsdpRequestHandler.
     * @param handler the handler to remove
     */
    public void removeRequestHandler(ISsdpRequestHandler handler) {
        handlers.remove(handler);
    }

    /**
     * This method will notify all handlers when a new SsdpRequest comes in,<br/>
     * even if a SsdpRequest is already handled.
     * @param request
     */
    protected void notifyHandlers(SsdpRequest request) {
        for (ISsdpRequestHandler handler : handlers) {
            handler.handleSsdpRequest(request);
        }
    }

    public String getGroupAddress() {
        return group.getHostAddress();
    }

    public int getPort() {
        if (multicastSocket != null) {
            return multicastSocket.getLocalPort();
        }
        return port;
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

    /**
     * Test
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press 'q' to stop.\nCreating SSDP server...");
        SsdpMulticastServer server = null;
        try {
            server = new SsdpMulticastServer(
                    NetworkInterface.getByName("eth0"));
        } catch (IOException ex) {
            System.out.println("FAILED");
            ex.printStackTrace();
            System.exit(1);
        }
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
