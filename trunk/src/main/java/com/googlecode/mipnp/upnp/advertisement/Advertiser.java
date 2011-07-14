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
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Advertiser implements SsdpConstants {

    private RootDevice rootDevice;
    private MulticastSocket mcastSocket;
    private DatagramSocket ucastSocket;
    private Thread mcastSearchHandler;
    private Thread ucastSearchHandler;
    private SocketAddress mcastAddr;
    private SocketAddress ucastAddr;
    private NetworkInterface networkInterface;
    private Thread mcastAdvertiserThread;

    /**
     * Creates a new Advertiser.
     * @param rootDevice the root device this advertiser is for
     * @param bindaddr the local address to bind to
     * @param networkInterface the network interface to use for multicast advertisement
     */
    public Advertiser(
            RootDevice rootDevice,
            InetAddress bindaddr,
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
        this.ucastAddr = new InetSocketAddress(bindaddr, SSDP_DEFAULT_PORT);
    }

    /**
     * Start advertising.
     * @throws SocketException if one of the sockets could not be opened, or could not bind.
     * @throws IOException if an I/O exception occurs
     */
    public void start() throws SocketException, IOException {
        this.mcastSocket = new MulticastSocket(mcastAddr);
        mcastSocket.setReuseAddress(true);
        mcastSocket.setTimeToLive(SSDP_DEFAULT_TTL);
        mcastSocket.joinGroup(mcastAddr, networkInterface);
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

    /**
     * Stop advertising.
     */
    public void stop() {
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
        if (mcastSocket != null) {
            try {
                mcastSocket.leaveGroup(mcastAddr, networkInterface);
            } catch (IOException ex) {
                // Ignore
            }
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
    }
}
