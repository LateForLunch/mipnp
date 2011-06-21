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
 * UnicastAdvertiser.java
 * Created on Jun 21, 2011, 2:55:35 PM
 */
package domain.upnp.advertisement;

import domain.ssdp.SsdpConstants;
import domain.upnp.IDevice;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class UnicastAdvertiser implements SsdpConstants, Runnable {

    private IDevice rootDevice;
    private InetAddress unicastAddr;
    private int port;
    private DatagramSocket socket;

    public UnicastAdvertiser(IDevice rootDevice) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public UnicastAdvertiser(IDevice rootDevice,
            InetAddress unicastAddr, int port) throws IOException {

        this.rootDevice = rootDevice;
        this.unicastAddr = unicastAddr;
        this.port = port;
        this.socket = new DatagramSocket(port);
    }

    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stop() throws IOException {
        socket.close();
    }
}
