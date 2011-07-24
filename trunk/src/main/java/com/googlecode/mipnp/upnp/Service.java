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

import java.net.URI;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public interface Service {

    /*
     * Returns the vendor domain name of the service.
     * @return the vendor domain name of the service
     */
//    String getVendorDomainName();

    /**
     * Returns the type of the service.
     * @return the type of the service
     */
    String getType();

    /**
     * Returns the identifier of the service.
     * @return the identifier of the service
     */
    String getId();

    /**
     * Returns the version of the service.
     * @return the version of the service
     */
    int getVersion();

    /**
     * Returns the type of the service as a URN.<br/>
     * <br/>
     * Format for standard service types defined by a UPnP Forum working committee:<br/>
     * "urn:schemas-upnp-org:service:<i>serviceType</i>:<i>ver</i>"<br/>
     * <br/>
     * Format for non-standard service types specified by UPnP vendors:<br/>
     * "urn:<i>domain-name</i>:service:<i>serviceType</i>:<i>ver</i>"
     * @return the type of the service as a URN
     */
    String getTypeAsUrn();

    /**
     * Returns the identifier of the service as a URN.<br/>
     * <br/>
     * Format for standard services defined by a UPnP Forum working committee:<br/>
     * "urn:upnp-org:serviceId:<i>serviceID</i>"<br/>
     * <i>Note that upnp-org is used instead of schemas-upnp-org in this case
     * because an XML schema is not defined for each service ID.</i><br/>
     * <br/>
     * Format for non-standard services specified by UPnP vendors:<br/>
     * "urn:<i>domain-name</i>:serviceId:<i>serviceID</i>"
     * @return the identifier of the service as a URN
     */
    String getIdAsUrn();

    /**
     * Returns the service description URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.
     * @return the relative description URI of the service
     */
    URI getDescriptionUri();

    /**
     * Sets the service description URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.
     * @param descriptionUri the relative description URI of the service
     */
    void setDescriptionUri(URI descriptionUri);

    /**
     * Returns the service control URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.
     * @return the relative control URI of the service
     */
    URI getControlUri();

    /**
     * Sets the service control URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.
     * @param controlUri the relative control URI of the service
     */
    void setControlUri(URI controlUri);

    /**
     * Returns the service eventing URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.<br/>
     * Must be unique within the device;
     * any two services must not have the same URI for eventing.
     * @return the relative eventing URI of the service
     */
    URI getEventUri();

    /**
     * Sets the service eventing URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.<br/>
     * Must be unique within the device;
     * any two services must not have the same URI for eventing.
     * @param eventUri the relative eventing URI of the service
     */
    void setEventUri(URI eventUri);

    /**
     * Returns the actions of the service.
     * @return the actions of the service
     */
    List<Action> getActions();

    /**
     * Returns the state variables of the service.
     * @return the state variables of the service
     */
    List<StateVariable> getStateVariables();
}
