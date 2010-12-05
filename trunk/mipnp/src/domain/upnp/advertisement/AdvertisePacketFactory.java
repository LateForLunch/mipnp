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
 * AdvertisePacketFactory.java
 * Created on Dec 5, 2010, 12:03:56 PM
 */
package domain.upnp.advertisement;

import domain.ssdp.SsdpConstants;
import domain.ssdp.SsdpRequest;
import domain.tools.ServerTools;
import domain.upnp.Device;
import domain.upnp.Service;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class AdvertisePacketFactory implements SsdpConstants {

    private static final int MAX_CONFIG_ID = 16777215;
    private static int configId = 0;

    public static SsdpRequest[] createMulticastAdvertiseSet(
            Device rootDevice, int maxAge) {

        List<SsdpRequest> list = new ArrayList<SsdpRequest>();
        // Root device
        list.add(createAdvertisePacket(rootDevice, maxAge,
                "upnp:rootdevice", rootDevice.getUdn() + "::upnp:rootdevice"));
        list.add(createAdvertisePacket(rootDevice, maxAge,
                rootDevice.getUdn(), rootDevice.getUdn()));
        list.add(createAdvertisePacket(rootDevice, maxAge, // TODO
                "urn:schemas-upnp-org:device:deviceType:ver " +
                "or urn:domain-name:device:deviceType:ver",
                "uuid:device-UUID::urn:schemas-upnp-org:device:deviceType:ver " +
                "or uuid:device-UUID::urn:domain-name:device:deviceType:ver"));
        for (Service s : rootDevice.getServices()) {
            list.add(createAdvertisePacket(rootDevice, maxAge, // TODO
                    "urn:schemas-upnp-org:service:serviceType:ver " +
                    "or urn:domain-name:service:serviceType:ver",
                    "uuid:device-UUID::urn:schemas-upnp-org:service:serviceType:ver " +
                    "or uuid:device-UUID::urn:domain-name:service:serviceType:ver"));
        }

        // Don't support embedded devices for now
//        for (Device d : rootDevice.getEmbeddedDevices()) {
//            list.add(createAdvertisePacket(rootDevice, maxAge,
//                    d.getUdn(), d.getUdn()));
//            list.add(createAdvertisePacket(rootDevice, maxAge, // TODO
//                    "urn:schemas-upnp-org:device:deviceType:ver " +
//                    "or urn:domain-name:device:deviceType:ver",
//                    "uuid:device-UUID::urn:schemas-upnp-org:device:deviceType:ver " +
//                    "or uuid:device-UUID::urn:domain-name:device:deviceType:ver"));
//        }

        // Prevent caching of configuration (for now)
        configId++;
        if (configId > MAX_CONFIG_ID) {
            configId = 0;
        }
        SsdpRequest[] ret = new SsdpRequest[list.size()];
        list.toArray(ret);
        return ret;
    }

    private static SsdpRequest createAdvertisePacket(
            Device rootDevice, int maxAge, String nt, String usn) {

        SsdpRequest request = new SsdpRequest();
        request.setMethod(NOTIFY);
        try {
            request.setRequestUri(new URI("*"));
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        request.setVersion(HTTP_VERSION_1_1);
        request.setHeader(HOST, SSDP_DEFAULT_ADDRESS + ":" + SSDP_DEFAULT_PORT);
        request.setHeader("CACHE-CONTROL", "max-age=" + maxAge);
        request.setHeader("LOCATION", rootDevice.getDescriptionUrl().toString());
        request.setHeader("NT", nt);
        request.setHeader("NTS", "ssdp:alive");
        request.setHeader("SERVER",
                ServerTools.getOsName() + "/" + ServerTools.getOsVersion() +
                "UPnP/1.1 MiPnP/0.1"); // TODO: version (low priority)
        request.setHeader("USN", usn);
        request.setHeader("BOOTID.UPNP.ORG", "1"); // TODO (low priority)
        request.setHeader("CONFIGID.UPNP.ORG", String.valueOf(configId)); // TODO (low priority)
//        request.setHeader("SEARCHPORT.UPNP.ORG", "number identifies port on which device responds to unicast M-SEARCH"); // TODO (low priority)
        return request;
    }
}
