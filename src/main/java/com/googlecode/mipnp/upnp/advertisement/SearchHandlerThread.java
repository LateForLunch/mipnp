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
 * SearchHandlerThread.java
 * Created on Jun 21, 2011, 5:26:10 PM
 */
package com.googlecode.mipnp.upnp.advertisement;

import com.googlecode.mipnp.http.MalformedHttpPacketException;
import com.googlecode.mipnp.ssdp.SsdpConstants;
import com.googlecode.mipnp.ssdp.SsdpRequest;
import com.googlecode.mipnp.upnp.RootDevice;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class SearchHandlerThread implements Runnable, SsdpConstants {

    private RootDevice rootDevice;
    private MulticastSocket socket;

    public SearchHandlerThread(RootDevice rootDevice, MulticastSocket socket) {
        this.rootDevice = rootDevice;
        this.socket = socket;
    }

    public void run() {
        while (!Thread.interrupted()) {
            byte[] buf = new byte[SSDP_DEFAULT_BUF_SIZE];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(recv);
                System.out.println("SsdpRequest received");
                ByteArrayInputStream bais =
                        new ByteArrayInputStream(
                        recv.getData(), recv.getOffset(), recv.getLength());
                SsdpRequest request = new SsdpRequest(bais);
                // TODO: handle request
            } catch (MalformedHttpPacketException ex) {
                // Ignore packet
            } catch (SocketException ex) {
                // Socket closed
                return;
            } catch (IOException ex) {
                ex.printStackTrace(); // TODO
            }
        }
    }
}
