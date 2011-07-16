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
import com.googlecode.mipnp.upnp.RootDevice;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Advertiser implements SsdpConstants {

    private RootDevice rootDevice;
    private MulticastSocket socket;
    private InetSocketAddress bindAddr;
    private InetSocketAddress mcastAddr;
    private NetworkInterface networkInterface;
    private Thread searchHandlerThread;
    private Thread advertiserThread;

    /**
     * Creates a new Advertiser.
     * @param rootDevice the root device this advertiser is for
     * @param bindAddr the local address to bind to
     * @param networkInterface the network interface to use for multicast advertisement
     */
    public Advertiser(
            RootDevice rootDevice,
            InetAddress bindAddr,
            NetworkInterface networkInterface) {

        this.rootDevice = rootDevice;
        this.networkInterface = networkInterface;
        InetAddress mcastInetAddr = null;
        try {
            mcastInetAddr = InetAddress.getByName(SSDP_DEFAULT_MULTICAST_ADDRESS);
        } catch (UnknownHostException ex) {
            // This should not happen
        }
        this.mcastAddr = new InetSocketAddress(mcastInetAddr, SSDP_DEFAULT_PORT);
        this.bindAddr = new InetSocketAddress(bindAddr, SSDP_DEFAULT_PORT);
    }

    /**
     * Start advertising.
     * @throws SocketException if one of the sockets could not be opened, or could not bind.
     * @throws IOException if an I/O exception occurs
     */
    public void start() throws SocketException, IOException {
        this.socket = new MulticastSocket(bindAddr.getPort());
        socket.setInterface(bindAddr.getAddress());
        socket.setNetworkInterface(networkInterface);
        socket.setTimeToLive(SSDP_DEFAULT_TTL);
        socket.joinGroup(mcastAddr.getAddress());

        this.searchHandlerThread = new Thread(
                new SearchHandlerThread(rootDevice, socket));
        searchHandlerThread.setName("Search Handler");

        this.advertiserThread = new Thread(
                new MulticastAdvertiserThread(rootDevice, socket));
        advertiserThread.setName("Multicast Advertiser");

        searchHandlerThread.start();
        advertiserThread.start();
    }

    /**
     * Stop advertising.
     */
    public void stop() {
        if (socket != null) {
            try {
                socket.leaveGroup(mcastAddr.getAddress());
            } catch (IOException ex) {
                // Ignore
            }
            socket.close();
            advertiserThread.interrupt();
            try {
                searchHandlerThread.join();
                advertiserThread.join();
            } catch (InterruptedException ex) {
                // This should not happen
            }
            socket = null;
            searchHandlerThread = null;
            advertiserThread = null;
        }
    }
}
