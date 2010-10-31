/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche, Tijl Van Assche
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
package domain;

/**
 * Created on Oct 20, 2010, 3:53:17 PM
 * 
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 * @author Tijl Van Assche <tijlvanassche@gmail.com>
 */
public class SsdpPacket implements IPacket, HttpConstants {

    public static final int METHOD_NOTIFY = 0;
    public static final int METHOD_M_SEARCH = 1;
    public static final int TYPE_STATUS_OK = 2;

    private static final String[] START_LINES = {
        "NOTIFY * " + HTTP_VERSION + CRLF,
        "M-SEARCH * " + HTTP_VERSION + CRLF,
        HTTP_VERSION + " 200 " + HTTP_STATUS.get(200) + CRLF
    };

    private static final String HEADER_HOST =
            "HOST: 239.255.255.250:1900" + CRLF;
    private static final String HEADER_CACHE_CONTROL =
            "CACHE-CONTROL: max-age = %s" + CRLF;
    private static final String HEADER_LOCATION =
            "LOCATION: %s" + CRLF;

    /*
     * SSDP format:
     * Start line
     * - NOTIFY * HTTP/1.1\r\n
     * - M-SEARCH * HTTP/1.1\r\n
     * - HTTP/1.1 200 OK\r\n
     *
     * SSDP Header
     * Example: HOST: 239.255.255.250:1900
     */

    /*
     * Advertisement - Device available (p29)
     * NOTIFY * HTTP/1.1
     * HOST: 239.255.255.250:1900
     * CACHE-CONTROL: max-age = 'seconds until advertisement expires'
     * LOCATION: 'URL for UPnP description for root device'
     * NT: 'notification type'
     * NTS: ssdp:alive
     * SERVER: 'OS/version' UPnP/1.1 'product/version'
     * USN: 'composite identifier for the advertisement'
     * BOOTID.UPNP.ORG: 'number increased each time device sends an initial announce or an update message'
     * CONFIGID.UPNP.ORG: 'number used for caching description information'
     * SEARCHPORT.UPNP.ORG: 'number identifies port on which device responds to unicast M-SEARCH'
     * 'Blank line'
     */
    private String startLine;
    private String header;
    private String rawData;

    public SsdpPacket(int type) {
        this.startLine = START_LINES[type];
        this.header = HEADER_HOST;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getData() {
        return startLine + header + getRawData();
    }
}
