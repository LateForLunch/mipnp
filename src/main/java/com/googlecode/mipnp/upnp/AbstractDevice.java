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
 * AbstractDevice.java
 * Created on Jun 24, 2011, 6:02:15 PM
 */
package com.googlecode.mipnp.upnp;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public abstract class AbstractDevice implements IDevice {

    private int majorVersion;
    private int minorVersion;
    private String uniformResourceName;
    private String friendlyName;
    private String manufacturer;
    private URL manufacturerUrl;
    private String modelDescription;
    private String modelName;
    private String modelNumber;
    private URL modelUrl;
    private String serialNumber;
    private UUID uuid;
    private String universalProductCode;
    private List<IService> services;
    private List<IDevice> embeddedDevices;
    private URL presentationUrl;

    public AbstractDevice() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public String getUniformResourceName() {
        return uniformResourceName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public URL getManufacturerUrl() {
        return manufacturerUrl;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public URL getModelUrl() {
        return modelUrl;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUniversalProductCode() {
        return universalProductCode;
    }

    public Iterable<IService> getServices() {
        return services;
    }

    public Iterable<IDevice> getEmbeddedDevices() {
        return embeddedDevices;
    }

    public URL getPresentationUrl() {
        return presentationUrl;
    }
}
