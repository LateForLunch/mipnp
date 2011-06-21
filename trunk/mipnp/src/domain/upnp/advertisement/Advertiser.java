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
package domain.upnp.advertisement;

import domain.ssdp.SsdpConstants;
import domain.tools.NetworkInterfaceTools;
import domain.upnp.IDevice;
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

    private IDevice rootDevice;
    private MulticastSocket mcastSocket;
    private DatagramSocket ucastSocket;
    private Thread mcastAdThread;
    private Thread ucastAdThread;
    private SocketAddress mcastAddr;
    private SocketAddress ucastAddr;
    private NetworkInterface nic;

    public Advertiser(IDevice rootDevice, NetworkInterface nic)
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
        mcastSocket.joinGroup(mcastAddr, nic);
        this.mcastAdThread =
                new Thread(new AdvertiserThread(rootDevice, mcastSocket));

        this.ucastSocket = new DatagramSocket(ucastAddr);
        this.ucastAdThread =
                new Thread(new AdvertiserThread(rootDevice, ucastSocket));

        mcastAdThread.start();
        ucastAdThread.start();
    }

    public void stop() throws IOException {
        if (mcastSocket != null) {
            mcastSocket.leaveGroup(mcastAddr, nic);
            mcastSocket.close();
        }
        if (ucastSocket != null) {
            ucastSocket.close();
        }
        try {
            mcastAdThread.join();
            ucastAdThread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
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
