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
 * RootDevice.java
 * Created on Jun 24, 2011, 5:30:55 PM
 */
package com.googlecode.mipnp.upnp;

import java.net.URL;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public interface RootDevice extends Device {

    /**
     * Returns the boot identifier.<br/>
     * This is also known as BOOTID.UPNP.ORG.<br/>
     * The value must be a non-negative 31-bit integer.
     * @return the boot identifier
     */
    int getBootId();

    /**
     * Returns the configuration identifier.<br/>
     * This is also known as CONFIGID.UPNP.ORG.<br/>
     * The value must be a non-negative 31-bit integer.
     * @return the configuration identifier
     */
    int getConfigId();

    /**
     * Returns a URL that points to the XML file that contains the description
     * of the root device, it's embedded devices and all the services.
     * @return 
     */
    URL getDescriptionUrl();
}
