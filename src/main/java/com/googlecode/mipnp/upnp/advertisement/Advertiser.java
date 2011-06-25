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
 * Advertiser.java
 * Created on Dec 5, 2010, 11:29:37 AM
 */
package com.googlecode.mipnp.upnp.advertisement;

import com.googlecode.mipnp.ssdp.SsdpConstants;
import com.googlecode.mipnp.tools.NetworkInterfaceTools;
import com.googlecode.mipnp.upnp.IRootDevice;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Advertiser implements SsdpConstants {

    private IRootDevice rootDevice;
    private MulticastSocket mcastSocket;
    private DatagramSocket ucastSocket;
    private Thread mcastSearchHandler;
    private Thread ucastSearchHandler;
    private SocketAddress mcastAddr;
    private SocketAddress ucastAddr;
    private NetworkInterface nic;
    private Thread mcastAdvertiserThread;

    public Advertiser(IRootDevice rootDevice, NetworkInterface nic)
            throws UnknownHostException, SocketException {

        this.rootDevice = rootDevice;
        this.nic = nic;
        InetAddress mcastInetAddr =
                InetAddress.getByName(SSDP_DEFAULT_MULTICAST_ADDRESS);
        this.mcastAddr = new InetSocketAddress(mcastInetAddr, SSDP_DEFAULT_PORT);
        InetAddress ucastInetAddress = NetworkInterfaceTools.interfaceToIp(nic);
        this.ucastAddr = new InetSocketAddress(ucastInetAddress, SSDP_DEFAULT_PORT);
    }

    public void start() throws SocketException, IOException {
        this.mcastSocket = new MulticastSocket(mcastAddr);
        mcastSocket.setReuseAddress(true);
        mcastSocket.setTimeToLive(SSDP_DEFAULT_TTL);
        mcastSocket.joinGroup(mcastAddr, nic);
        this.ucastSocket = new DatagramSocket(ucastAddr);

        this.mcastSearchHandler = new Thread(
                new SearchHandlerThread(rootDevice, mcastSocket));
        mcastSearchHandler.setName("Multicast Search Handler");

        this.ucastSearchHandler = new Thread(
                new SearchHandlerThread(rootDevice, ucastSocket));
        ucastSearchHandler.setName("Unicast Search Handler");

        this.mcastAdvertiserThread = new Thread(
                new MulticastAdvertiserThread(rootDevice, mcastSocket));
        mcastAdvertiserThread.setName("Multicast Advertiser");

        mcastSearchHandler.start();
        ucastSearchHandler.start();
        mcastAdvertiserThread.start();
    }

    public void stop() throws IOException {
        if (mcastSocket != null) {
            mcastSocket.leaveGroup(mcastAddr, nic);
            mcastSocket.close();
            mcastAdvertiserThread.interrupt();
            try {
                mcastSearchHandler.join();
                mcastAdvertiserThread.join();
            } catch (InterruptedException ex) {
                // This should not happen
            }
            mcastSocket = null;
            mcastSearchHandler = null;
            mcastAdvertiserThread = null;
        }
        if (ucastSocket != null) {
            ucastSocket.close();
            try {
                ucastSearchHandler.join();
            } catch (InterruptedException ex) {
                // This should not happen
            }
            ucastSocket = null;
            ucastSearchHandler = null;
        }
    }

    public static void main(String[] args) {
        String s_nic = "eth0";
        NetworkInterface nic = null;
        try {
            nic = NetworkInterface.getByName(s_nic);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press 'q' to stop.\nCreating SSDP listener...");
        Advertiser advertiser = null;
        try {
            advertiser = new Advertiser(null, nic);
            advertiser.start();
        } catch (IOException ex) {
            System.out.println(" FAILED\n");
            ex.printStackTrace();
            System.exit(1);
        }
        System.out.println(" OK\n");
        while (!(scanner.nextLine().equalsIgnoreCase("q"))) {
            System.out.println("Unknown command.\nPress 'q' to stop.\n");
        }
        try {
            advertiser.stop();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
