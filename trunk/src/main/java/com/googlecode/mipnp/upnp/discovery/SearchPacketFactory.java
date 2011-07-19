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
 * SearchPacketFactory.java
 * Created on Jul 19, 2011, 5:01:54 PM
 */
package com.googlecode.mipnp.upnp.discovery;

import com.googlecode.mipnp.ssdp.SsdpConstants;
import com.googlecode.mipnp.ssdp.SsdpRequest;
import com.googlecode.mipnp.ssdp.SsdpResponse;
import com.googlecode.mipnp.tools.ServerTools;
import com.googlecode.mipnp.upnp.Device;
import com.googlecode.mipnp.upnp.RootDevice;
import com.googlecode.mipnp.upnp.Service;
import com.googlecode.mipnp.upnp.UpnpTools;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class SearchPacketFactory implements SsdpConstants {

    public static List<SsdpResponse> createSearchResponseSet(
            RootDevice rootDevice,
            int advertisementDuration,
            SsdpRequest request) {

        List<SsdpResponse> resp = new ArrayList<SsdpResponse>();
        String st = request.getHeader("ST");

        if (st.equals("ssdp:all")) {
            return createAllSearchResponses(rootDevice, advertisementDuration);
        } else if (st.equals("upnp:rootdevice")) {
            resp.add(createSearchResponse(advertisementDuration,
                    rootDevice.getDescriptionUrl(), "upnp:rootdevice",
                    "uuid:" + rootDevice.getUuid() + "::upnp:rootdevice",
                    rootDevice.getBootId(), rootDevice.getConfigId()));
            return resp;
        } else if (st.startsWith("uuid:")) {
            String uuid = st.substring(5);
            if (uuid.equals(rootDevice.getUuid().toString())) {
                resp.add(createSearchResponse(advertisementDuration,
                        rootDevice.getDescriptionUrl(), st,
                        st, rootDevice.getBootId(), rootDevice.getConfigId()));
                return resp;
            }
            for (Device embDev : rootDevice.getEmbeddedDevices()) {
                if (uuid.equals(embDev.getUuid().toString())) {
                    resp.add(createSearchResponse(advertisementDuration,
                            rootDevice.getDescriptionUrl(), st,
                            st, rootDevice.getBootId(), rootDevice.getConfigId()));
                    return resp;
                }
            }
        } else if (st.startsWith("urn:")) {
            // TODO: check version
            if (st.equals(UpnpTools.getTypeAsUrn(rootDevice))) {
                resp.add(createSearchResponse(advertisementDuration,
                        rootDevice.getDescriptionUrl(), st,
                        st, rootDevice.getBootId(), rootDevice.getConfigId()));
                return resp;
            }
            for (Service service : rootDevice.getServices()) {
                if (st.equals(UpnpTools.getTypeAsUrn(service))) {
                    resp.add(createSearchResponse(advertisementDuration,
                            rootDevice.getDescriptionUrl(), st,
                            st, rootDevice.getBootId(), rootDevice.getConfigId()));
                    return resp;
                }
            }
            for (Device embDev : rootDevice.getEmbeddedDevices()) {
                if (st.equals(UpnpTools.getTypeAsUrn(embDev))) {
                    resp.add(createSearchResponse(advertisementDuration,
                            rootDevice.getDescriptionUrl(), st,
                            st, rootDevice.getBootId(), rootDevice.getConfigId()));
                    return resp;
                }
                for (Service service : embDev.getServices()) {
                    if (st.equals(UpnpTools.getTypeAsUrn(service))) {
                        resp.add(createSearchResponse(advertisementDuration,
                                rootDevice.getDescriptionUrl(), st,
                                st, rootDevice.getBootId(), rootDevice.getConfigId()));
                        return resp;
                    }
                }
            }
        }
        return resp;
    }

    public static List<SsdpResponse> createAllSearchResponses(
            RootDevice rootDevice, int advertisementDuration) {

        List<SsdpResponse> set = new ArrayList<SsdpResponse>();
        // Root device
        set.add(createSearchResponse(advertisementDuration,
                rootDevice.getDescriptionUrl(), "upnp:rootdevice",
                "uuid:" + rootDevice.getUuid() + "::upnp:rootdevice",
                rootDevice.getBootId(), rootDevice.getConfigId()));
        set.add(createSearchResponse(advertisementDuration,
                rootDevice.getDescriptionUrl(), "uuid:" + rootDevice.getUuid(),
                "uuid:" + rootDevice.getUuid(),
                rootDevice.getBootId(), rootDevice.getConfigId()));
        String type = UpnpTools.getTypeAsUrn(rootDevice);
        set.add(createSearchResponse(advertisementDuration,
                rootDevice.getDescriptionUrl(), type,
                "uuid:" + rootDevice.getUuid() + "::" + type,
                rootDevice.getBootId(), rootDevice.getConfigId()));
        // Services
        for (Service service : rootDevice.getServices()) {
            type = UpnpTools.getTypeAsUrn(service);
            set.add(createSearchResponse(advertisementDuration,
                    rootDevice.getDescriptionUrl(), type,
                    "uuid:" + rootDevice.getUuid() + "::" + type,
                    rootDevice.getBootId(), rootDevice.getConfigId()));
        }
        // Embedded Devices
        for (Device embDev : rootDevice.getEmbeddedDevices()) {
            set.add(createSearchResponse(advertisementDuration,
                    rootDevice.getDescriptionUrl(), "uuid:" + embDev.getUuid(),
                    "uuid:" + embDev.getUuid(),
                    rootDevice.getBootId(), rootDevice.getConfigId()));
            type = UpnpTools.getTypeAsUrn(embDev);
            set.add(createSearchResponse(advertisementDuration,
                    rootDevice.getDescriptionUrl(), type,
                    "uuid:" + embDev.getUuid() + "::" + type,
                    rootDevice.getBootId(), rootDevice.getConfigId()));
            // Services
            for (Service service : embDev.getServices()) {
                type = UpnpTools.getTypeAsUrn(service);
                set.add(createSearchResponse(advertisementDuration,
                        rootDevice.getDescriptionUrl(), type,
                        "uuid:" + embDev.getUuid() + "::" + type,
                        rootDevice.getBootId(), rootDevice.getConfigId()));
            }
        }
        return set;
    }

    public static SsdpResponse createSearchResponse(
            int advertisementDuration, URL rootDeviceDescriptionUrl,
            String searchTarget, String uniqueServiceName,
            int bootId, int configId) {

        SsdpResponse response = new SsdpResponse();
        response.setVersion(HTTP_VERSION_1_1);
        response.setStatusCode(STATUS_OK);
        response.setHeader("CACHE-CONTROL", "max-age=" + advertisementDuration);
        // TODO: DATE
        response.setHeader("EXT", "");
        response.setHeader("LOCATION", rootDeviceDescriptionUrl.toString());
        response.setHeader("SERVER",
                ServerTools.getOsName() + "/" + ServerTools.getOsVersion() +
                " UPnP/1.1 MiPnP/1.0"); // TODO: UPnP version and MiPnP version
        response.setHeader("ST", searchTarget);
        response.setHeader("USN", uniqueServiceName);
        response.setHeader("BOOTID.UPNP.ORG", String.valueOf(bootId));
        response.setHeader("CONFIGID.UPNP.ORG", String.valueOf(configId));
        return response;
    }
}
