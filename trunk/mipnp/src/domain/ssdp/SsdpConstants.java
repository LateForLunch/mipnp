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
 * SsdpConstants.java
 * Created on Dec 4, 2010, 4:15:14 PM
 */
package domain.ssdp;

import domain.http.HttpConstants;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public interface SsdpConstants extends HttpConstants {

    public static final String SSDP_DEFAULT_ADDRESS = "239.255.255.250";
    public static final int SSDP_DEFAULT_PORT = 1900;
    public static final int SSDP_DEFAULT_TTL = 2;
    public static final int SSDP_DEFAULT_MAX_AGE = 1800;

    // TODO: check if this is right
    public static final int SSDP_DEFAULT_BUF_SIZE = 1024;

    /*
     * Methods
     */
    public static final String NOTIFY = "NOTIFY";
    public static final String M_SEARCH = "M-SEARCH";
}
