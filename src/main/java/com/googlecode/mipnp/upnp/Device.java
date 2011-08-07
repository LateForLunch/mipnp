/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010, 2011  Jochem Van denbussche
 *
 * This file is part of MiPnP.
 *
 * MiPnP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MiPnP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Device.java
 * Created on Jun 21, 2011, 2:00:15 PM
 */
package com.googlecode.mipnp.upnp;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public interface Device {

    /**
     * Returns the major version of the UPnP Device Architecture.
     * @return the major version of the UPnP Device Architecture
     */
    int getMajorUpnpVersion();

    /**
     * Returns the minor version of the UPnP Device Architecture.
     * @return the minor version of the UPnP Device Architecture
     */
    int getMinorUpnpVersion();

    // TODO: URLBase

    /*
     * Returns the vendor domain name of the device.
     * @return the vendor domain name of the device
     */
//    String getVendorDomainName();

    /**
     * Returns the type of the device.
     * @return the type of the device
     */
    String getType();

    /**
     * Returns the version of the device.
     * @return the version of the device
     */
    int getVersion();

    /**
     * Returns the type of the device as a URN.<br/>
     * <br/>
     * Format for standard devices defined by a UPnP Forum working committee:<br/>
     * "urn:schemas-upnp-org:device:<i>deviceType</i>:<i>ver</i>"<br/>
     * <br/>
     * Format for non-standard devices specified by UPnP vendors:<br/>
     * "urn:<i>domain-name</i>:device:<i>deviceType</i>:<i>ver</i>"
     * @return 
     */
    String getTypeAsUrn();

    /**
     * Returns the friendly name of the device.<br/>
     * This is a short description for the end user.<br/>
     * The friendly name should be &lt; 64 characters.
     * @return the friendly name of the device
     */
    String getFriendlyName();

    /**
     * Returns the manufacturer's name.<br/>
     * This is an optional value in the device description.<br/>
     * This should be &lt; 64 characters.
     * @return the manufacturer's name or null if the manufacturer's name is not set
     */
    String getManufacturer();

    /**
     * Returns a URL to the web site of the manufacturer.<br/>
     * This is an optional value in the device description.<br/>
     * @return a URL to the web site of the manufacturer or null if the URL is not set
     */
    URL getManufacturerUrl();

    /**
     * Returns the long description for the end user.<br/>
     * This is a recommended value in the device description.<br/>
     * This should be &lt; 128 characters.
     * @return the long description of the device or null if the description is not set
     */
    String getModelDescription();

    /**
     * Returns the model name of the device.<br/>
     * This should be &lt; 32 characters.
     * @return the model name of the device
     */
    String getModelName();

    /**
     * Returns the model number of the device.<br/>
     * This is a recommended value in the device description.<br/>
     * This should be &lt; 32 characters.
     * @return the model number of the device or null if the model number is not set
     */
    String getModelNumber();

    /**
     * Returns the model URL of the device.<br/>
     * This URL points to the web site of the model.<br/>
     * This is an optional value in the device description.<br/>
     * @return the model URL of the device or null if the URL is not set
     */
    URL getModelUrl();

    /**
     * Returns the serial number of the device.<br/>
     * This is a recommended value in the device description.<br/>
     * This should be &lt; 64 characters.
     * @return the serial number of the device or null if the serial number is not set
     */
    String getSerialNumber();

//    String getUniqueDeviceName(); // = "uuid:" + getUUID();

    /**
     * Returns the UUID of the device.<br/>
     * The UUID must remain fixed over time.
     * @return the UUID of the device
     */
    UUID getUuid();

    /**
     * Returns the universal product code (UPC) of the device.<br/>
     * The UPC is a 12-digit, all-numeric code that identifies the consumer package.<br/>
     * This is an optional value in the device description.<br/>
     * @return the universal product code (UPC) or null if the UPC is not set
     */
    String getUniversalProductCode();

    // TODO: iconList

    /**
     * Returns the services of the device.
     * @return the services of the device
     */
    List<Service> getServices();

    /**
     * Returns the embedded devices of the device.
     * @return the embedded devices of the device
     */
    List<Device> getEmbeddedDevices();

    /**
     * Returns the presentation URL of the device.<br/>
     * This is a recommended value in the device description.<br/>
     * @return the presentation URL or null if the URL is not set
     */
    URL getPresentationUrl();
}
