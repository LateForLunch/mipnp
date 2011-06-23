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
import domain.upnp.IDevice;
import domain.upnp.IService;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class AdvertisePacketFactory implements SsdpConstants {

    private static final int MAX_CONFIG_ID = 16777215;

    /**
     * Creates a list of {@link SsdpRequest} objects to advertise a root device,
     * all of its embedded devices and all the services.
     * @param rootDevice the root device
     * @param advertisementDuration the duration of the advertisement
     * @return a list of {@link SsdpRequest} objects
     */
    public static List<SsdpRequest> createAliveSet(
            IDevice rootDevice, int advertisementDuration) {

        List<SsdpRequest> set = new ArrayList<SsdpRequest>();
        // Root device
        set.add(createAlive(advertisementDuration,
                rootDevice.getDescriptionUrl(), "upnp:rootdevice",
                "uuid:" + rootDevice.getUuid() + "::upnp:rootdevice",
                rootDevice.getBootId(), rootDevice.getConfigId()));
        set.add(createAlive(advertisementDuration,
                rootDevice.getDescriptionUrl(), "uuid:" + rootDevice.getUuid(),
                "uuid:" + rootDevice.getUuid(),
                rootDevice.getBootId(), rootDevice.getConfigId()));
        set.add(createAlive(advertisementDuration,
                rootDevice.getDescriptionUrl(), rootDevice.getURN(),
                "uuid:" + rootDevice.getUuid() + "::" + rootDevice.getURN(),
                rootDevice.getBootId(), rootDevice.getConfigId()));
        // Services
        for (IService service : rootDevice.getServices()) {
            set.add(createAlive(advertisementDuration,
                    rootDevice.getDescriptionUrl(), service.getURN(),
                    "uuid:" + rootDevice.getUuid() + "::" + service.getURN(),
                    rootDevice.getBootId(), rootDevice.getConfigId()));
        }
        // Embedded Devices
        for (IDevice embDev : rootDevice.getEmbeddedDevices()) {
            set.add(createAlive(advertisementDuration,
                    rootDevice.getDescriptionUrl(), "uuid:" + embDev.getUuid(),
                    "uuid:" + embDev.getUuid(),
                    rootDevice.getBootId(), rootDevice.getConfigId()));
            set.add(createAlive(advertisementDuration,
                    rootDevice.getDescriptionUrl(), embDev.getURN(),
                    "uuid:" + embDev.getUuid() + "::" + embDev.getURN(),
                    rootDevice.getBootId(), rootDevice.getConfigId()));
            // Services
            for (IService service : embDev.getServices()) {
                set.add(createAlive(advertisementDuration,
                        rootDevice.getDescriptionUrl(), service.getURN(),
                        "uuid:" + embDev.getUuid() + "::" + service.getURN(),
                        rootDevice.getBootId(), rootDevice.getConfigId()));
            }
        }
        return set;
    }

    /**
     * Creates an {@link SsdpRequest} object to advertise a root device,
     * embedded device or service.
     * @param advertisementDuration the duration of the advertisement
     * @param rootDeviceDescriptionUrl the URL to the description of the root device
     * @param notificationType the notification type
     * @param uniqueServiceName the unique service name
     * @param bootId the boot ID (BOOTID.UPNP.ORG)
     * @param configId the configuration ID (CONFIGID.UPNP.ORG)
     * @return an {@link SsdpRequest} object
     */
    public static SsdpRequest createAlive(
            int advertisementDuration, URL rootDeviceDescriptionUrl,
            String notificationType, String uniqueServiceName,
            int bootId, int configId) {

        SsdpRequest request = new SsdpRequest();
        request.setMethod(METHOD_NOTIFY);
        try {
            request.setRequestUri(new URI("*"));
        } catch (URISyntaxException ex) {
            // This should not happen
        }
        request.setVersion(HTTP_VERSION_1_1);
        request.setHeader(HEADER_HOST,
                SSDP_DEFAULT_MULTICAST_ADDRESS + ":" + SSDP_DEFAULT_PORT);
        request.setHeader("CACHE-CONTROL", "max-age=" + advertisementDuration);
        request.setHeader("LOCATION", rootDeviceDescriptionUrl.toString());
        request.setHeader("NT", notificationType);
        request.setHeader("NTS", "ssdp:alive");
        request.setHeader("SERVER",
                ServerTools.getOsName() + "/" + ServerTools.getOsVersion() +
                " UPnP/1.1 MiPnP/0.1"); // TODO: MiPnP version
        request.setHeader("USN", uniqueServiceName);
        request.setHeader("BOOTID.UPNP.ORG", String.valueOf(bootId));
        request.setHeader("CONFIGID.UPNP.ORG", String.valueOf(configId));
        return request;
    }
}
