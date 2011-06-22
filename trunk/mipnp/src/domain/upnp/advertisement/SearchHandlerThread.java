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
package domain.upnp.advertisement;

import domain.ssdp.SsdpConstants;
import domain.upnp.IDevice;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class SearchHandlerThread implements Runnable, SsdpConstants {

    private IDevice rootDevice;
    private DatagramSocket socket;

    public SearchHandlerThread(IDevice rootDevice, DatagramSocket socket) {
        this.rootDevice = rootDevice;
        this.socket = socket;
    }

    public void run() {
        while (!Thread.interrupted()) {
            byte[] buf = new byte[SSDP_DEFAULT_BUF_SIZE];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(recv);
                System.out.println(new String(recv.getData())); // TODO
            } catch (SocketException ex) { // Socket closed
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}