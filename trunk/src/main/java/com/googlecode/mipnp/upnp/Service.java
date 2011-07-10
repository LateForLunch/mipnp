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
 * Service.java
 * Created on Jun 20, 2011, 3:10:41 PM
 */
package com.googlecode.mipnp.upnp;

import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public interface Service {

    /*
     * Returns the Uniform Resource Name (URN) of the service as a URI.<br/>
     * This is also known as the service type in the description XML.<br/>
     * <br/>
     * Format:<br/>
     * "urn:schemas-upnp-org:service:<i>serviceType</i>:<i>ver</i>"<br/>
     * or<br/>
     * "urn:<i>domain-name</i>:service:<i>serviceType</i>:<i>ver</i>"
     * @return the Uniform Resource Name of the service
     */
//    String getUniformResourceName();

    /*
     * Returns the service identifier as a URI.<br/>
     * This value must be unique within a device description.<br/>
     * <br/>
     * Format:<br/>
     * "urn:upnp-org:serviceId:<i>serviceID</i>"<br/>
     * or<br/>
     * "urn:<i>domain-name</i>:serviceId:<i>serviceID</i>"
     * @return the identifier of the service
     */
//    String getIdentifier();

    /*
     * Returns the service description URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.
     * @return the relative description URI of the service
     */
//    URI getDescriptionUri();

    /*
     * Returns the service control URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.
     * @return the relative control URI of the service
     */
//    URI getControlUri();

    /*
     * Returns the service eventing URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.<br/>
     * Must be unique within the device.
     * @return the relative eventing URI of the service
     */
//    URI getEventUri();

    String getVendorDomainName();

    String getType();

    String getId();

    String getVersion();

    List<Action> getActions();
}
