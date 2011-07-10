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
 * ConnectionManager.java
 * Created on Jun 30, 2011, 3:36:22 PM
 */
package com.googlecode.mipnp.upnp.mediaserver;

import com.googlecode.mipnp.upnp.AbstractService;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class ConnectionManager extends AbstractService {

    public ConnectionManager() {
        super("upnp.org", "ConnectionManager", "ConnectionManager", "1");
//        try {
//            setDescriptionUri(new URI("/connectionmanager.xml"));
//            setControlUri(new URI("/connectionmanager/control"));
//            setEventUri(new URI("/connectionmanager/event"));
//        } catch (URISyntaxException ex) {
//            // This should not happen
//        }
    }
}
