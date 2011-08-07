/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010, 2011  Jochem Van denbussche
 *
 * This file is part of MiPnP.
 *
 * MiPnP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MiPnP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * SearchHandler.java
 * Created on Jun 21, 2011, 5:26:10 PM
 */
package com.googlecode.mipnp.upnp.discovery;

import com.googlecode.mipnp.ssdp.SsdpConstants;
import com.googlecode.mipnp.upnp.RootDevice;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class SearchHandler implements Runnable, SsdpConstants {

    private static final int BUF_SIZE = 512;

    private RootDevice rootDevice;
    private MulticastSocket socket;
    private ExecutorService threadPool;

    public SearchHandler(RootDevice rootDevice, MulticastSocket socket) {
        this.rootDevice = rootDevice;
        this.socket = socket;
        this.threadPool = Executors.newCachedThreadPool();
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                byte[] buf = new byte[BUF_SIZE];
                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(recv);
                    threadPool.execute(
                            new SearchResponder(rootDevice, socket, recv));
                } catch (SocketException ex) {
                    // Socket closed
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace(); // TODO
                }
            }
        } finally {
            threadPool.shutdownNow();
        }
    }
}
