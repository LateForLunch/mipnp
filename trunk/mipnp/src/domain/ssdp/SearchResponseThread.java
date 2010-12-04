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
 * SearchResponseThread.java
 * Created on Oct 23, 2010, 4:10:44 PM
 */
package domain.ssdp;

import domain.upnp.Device;
import java.net.MulticastSocket;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SearchResponseThread implements Runnable {

    private Device rootDevice;
    private MulticastSocket socket;

    public SearchResponseThread(Device rootDevice, MulticastSocket socket) {
        setRootDevice(rootDevice);
        setSocket(socket);
    }

    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void setRootDevice(Device rootDevice) {
        this.rootDevice = rootDevice;
    }

    private void setSocket(MulticastSocket socket) {
        this.socket = socket;
    }
}
