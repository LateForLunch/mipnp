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
import domain.upnp.Device;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class AdvertisePacketFactory implements SsdpConstants {

    public static SsdpRequest[] createAdvertiseSet(Device rootDevice) {
        List<SsdpRequest> list = new ArrayList<SsdpRequest>();
        SsdpRequest request = createAdvertiseBase();
        request.setHeader(HOST, SSDP_DEFAULT_ADDRESS + ":" + SSDP_DEFAULT_PORT);
        request.setHeader("CACHE-CONTROL", "max-age=1800"); // TODO: don't hardcode 1800 here
        request.setHeader("LOCATION", "URL for UPnP description for root device"); // TODO
        request.setHeader("NT", "notification type"); // TODO
        request.setHeader("NTS", "ssdp:alive");
        request.setHeader("SERVER", "OS/version UPnP/1.1 product/version"); // TODO
        request.setHeader("USN", "composite identifier for the advertisement"); // TODO
        request.setHeader("BOOTID.UPNP.ORG", "number increased each time device sends an initial announce or an update message"); // TODO
        request.setHeader("CONFIGID.UPNP.ORG", "number used for caching description information");
        request.setHeader("SEARCHPORT.UPNP.ORG", "number identifies port on which device responds to unicast M-SEARCH"); // TODO
        return null;
    }

    private static SsdpRequest createAdvertiseBase() {
        SsdpRequest request = new SsdpRequest();
        request.setMethod(NOTIFY);
        try {
            request.setRequestUri(new URI("*"));
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        request.setVersion(HTTP_VERSION_1_1);
        return request;
    }
}
