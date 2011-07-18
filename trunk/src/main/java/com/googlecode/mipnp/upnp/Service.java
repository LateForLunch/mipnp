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

    /**
     * Returns the vendor domain name of the service.
     * @return the vendor domain name of the service
     */
    String getVendorDomainName();

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
    String getVersion();

    /**
     * Returns the service description URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.
     * @return the relative description URI of the service
     */
    URI getDescriptionUri();

    /**
     * Returns the service control URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.
     * @return the relative control URI of the service
     */
    URI getControlUri();

    /**
     * Returns the service eventing URI.<br/>
     * This must be relative to the URL at which
     * the device description is located.<br/>
     * Must be unique within the device.
     * @return the relative eventing URI of the service
     */
    URI getEventUri();

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
