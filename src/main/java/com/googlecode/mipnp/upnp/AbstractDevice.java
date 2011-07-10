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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public abstract class AbstractDevice implements Device {

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
    private List<Service> services;
    private List<Device> embeddedDevices;
    private URL presentationUrl;

    public AbstractDevice() {
        this.services = new ArrayList<Service>();
        this.embeddedDevices = new ArrayList<Device>();
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public String getUniformResourceName() {
        return uniformResourceName;
    }

    public void setUniformResourceName(String uniformResourceName) {
        this.uniformResourceName = uniformResourceName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public URL getManufacturerUrl() {
        return manufacturerUrl;
    }

    public void setManufacturerUrl(URL manufacturerUrl) {
        this.manufacturerUrl = manufacturerUrl;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public URL getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(URL modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUniversalProductCode() {
        return universalProductCode;
    }

    public void setUniversalProductCode(String universalProductCode) {
        this.universalProductCode = universalProductCode;
    }

    public List<Service> getServices() {
        return services;
    }

    public boolean addService(Service service) {
        return services.add(service);
    }

    public List<Device> getEmbeddedDevices() {
        return embeddedDevices;
    }

    public boolean addEmbeddedDevice(Device device) {
        return embeddedDevices.add(device);
    }

    public URL getPresentationUrl() {
        return presentationUrl;
    }

    public void setPresentationUrl(URL presentationUrl) {
        this.presentationUrl = presentationUrl;
    }
}
