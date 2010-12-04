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
 * SsdpServer.java
 * Created on Dec 4, 2010, 4:18:13 PM
 */
package domain.ssdp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SsdpServer implements SsdpConstants {

    private String groupAddress;
    private InetAddress group;
    private int port;
    private MulticastSocket multicastSocket;

    public SsdpServer() {
    }

    public void start() throws IOException {
        this.group = InetAddress.getByName(groupAddress);
        this.multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(group);
    }

    public void stop() throws IOException {
        multicastSocket.leaveGroup(group);
        multicastSocket.close();
        this.multicastSocket = null;
        this.group = null;
    }
}
