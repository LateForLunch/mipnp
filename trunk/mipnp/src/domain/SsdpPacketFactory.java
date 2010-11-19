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
        packets[0] = new SsdpPacket(0);
        packets[0].setRawData(
                "CACHE-CONTROL: max-age = 4" +  SsdpPacket.CRLF
                + "LOCATION: " + rootDevice.getUrlBase() + SsdpPacket.CRLF //checken welke URL
                + "NT: " + "upnp:rootdevice" + SsdpPacket.CRLF
                + "NTS: " + "ssdp:alive" + SsdpPacket.CRLF
                + "SERVER: " + System.getProperty("os.name")+"/"+System.getProperty("os.version")
                + " UPnP/1.0 "
                + rootDevice.getModelName() + "/" + rootDevice.getSerialNumber()
                /* kan ineen */+ SsdpPacket.CRLF
                + "USN: " + "uuid:"+rootDevice.getUdn() + SsdpPacket.CRLF
                );
        //packets[1]... NT: uuid:device-UUID, USN: uuid:device-UUID (for root device UUID)

        //packets[2]... NT: urn:schemas-upnp-org:device:deviceType:v or urn:domain-name:device:deviceType:v , USN:
        //              USN: uuid:device-UUID::urn:schemas-upnp-org:device:deviceType:v (of root device) or uuid:device-UUID::urn:domain-name:device:deviceType:v
        return packets;
    }

    public static IPacket[] createEmbeddedDeviceDiscMess(Device device) {
        IPacket[] packets = new IPacket[2];
        // p. 27 - Table 1-2
        //hebben we momenteel (nog) niet
        return packets;
    }

    public static IPacket createServiceDiscMess(Service service) {
        IPacket packet = null;
        // p. 27 - Table 1-3
        //new SsdpPacket(0).setRawData();
        //NT: urn:schemas-upnp-org:service:serviceType:v or urn:domain-name:service:serviceType:v
        //USN: uuid:device-UUID::urn:schemas-upnp-org:service:serviceType:v or uuid:device-UUID::urn:domain-name:service:serviceType:v
        return packet;
    }
}
