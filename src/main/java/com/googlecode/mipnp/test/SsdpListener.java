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
 * SsdpListener.java
 * Created on Oct 17, 2010, 3:51:19 PM
 */
package com.googlecode.mipnp.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SsdpListener implements Runnable {

    private static final String ADDRESS = "239.255.255.250";
    private static final int PORT = 1900;

    private InetAddress group;
    private SocketAddress socketAddress;
    private NetworkInterface nic;
    private MulticastSocket socket;

    private SsdpListener() throws IOException {
        this.group = InetAddress.getByName(ADDRESS);
        this.socketAddress = new InetSocketAddress(group, PORT);
        this.nic = NetworkInterface.getByName("eth0");
        this.socket = new MulticastSocket(PORT);
    }

    public void run() {
        try {
            socket.joinGroup(socketAddress, nic);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        while (true) {
            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(recv);
                System.out.println(new String(recv.getData()));
            } catch (SocketException ex) { // Socket closed
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            socket.leaveGroup(socketAddress, nic);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        socket.close();
    }

    /**
     * Starts the SSDP test. This should start an SSDP listener.<br/>
     * When there is SSDP traffic, the listener should print all the messages.
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press 'q' to stop.\nCreating SSDP listener...");
        SsdpListener listener = null;
        try {
            listener = new SsdpListener();
        } catch (IOException ex) {
            System.out.println(" FAILED\n");
            ex.printStackTrace();
            System.exit(1);
        }
        Thread thread = new Thread(listener);
        System.out.println(" OK\n");
        thread.start();
        while (!(scanner.nextLine().equalsIgnoreCase("q"))) {
            System.out.println("Unknown command.\nPress 'q' to stop.\n");
        }
        System.out.print("Sending interrupt...");
        thread.interrupt();
        System.out.println(" OK");
        System.out.print("Joining thread...");
        try {
            thread.join(3000);
        } catch (InterruptedException ex) {
            System.out.println(" FAILED");
            ex.printStackTrace();
        }
        System.out.println(" OK");
        System.out.print("Cleaning up...");
        listener.stop();
        System.out.println(" OK");
    }
}
