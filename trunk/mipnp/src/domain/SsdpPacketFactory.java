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
 * SsdpPacketFactory.java
 * Created on Oct 20, 2010, 4:45:56 PM
 */
package domain;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SsdpPacketFactory {

    public static IPacket[] createRootDeviceDiscMess(Device rootDevice) {
        IPacket[] packets = new IPacket[3];
        // p. 27 - Table 1-1
        return packets;
    }

    public static IPacket[] createEmbeddedDeviceDiscMess(Device device) {
        IPacket[] packets = new IPacket[2];
        // p. 27 - Table 1-2
        return packets;
    }

    public static IPacket createServiceDiscMess(Service service) {
        IPacket packet = null;
        // p. 27 - Table 1-3
        return packet;
    }
}
