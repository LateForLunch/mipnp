/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jeroen De Wilde, Jochem Van denbussche
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
 * Device.java
 * Created on Oct 23, 2010, 3:36:01 PM
 */
package domain.upnp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.Icon;

/**
 *
 * @author Jeroen De Wilde, Jochem Van denbussche
 */
public class Device {

    private String configId;
    private int upnpVersionMajor;
    private int upnpVersionMinor;
    private URL presentationURL;

    private URL baseURL; // Only for UPnP 1.0 devices
    private String type;
    private String version;
    private String friendlyName;
    private String manufacturer;
    private URL manufacturerURL;
    private String modelDescription;
    private String modelName;
    private String modelNumber;
    private URL modelURL;
    private String serialNumber;
    private String uniqueDeviceName;
    private String universalProductCode;
    private List<Icon> icons;
    private List<Service> services;
    private List<Device> embeddedDevices;

    private URL descriptionUrl;
    private UUID uuid;

    public Device() {
        this.icons = new ArrayList<Icon>();
        services = new ArrayList<Service>();
        embeddedDevices = new ArrayList<Device>();
    }

    public void addService(Service s) {
        if (!services.contains(s)) {
            services.add(s);
        }
    }

    /**
     * @return the urlBase
     */
    public URL getBaseURL() {
        return baseURL;
    }

    /**
     * @param urlBase the urlBase to set
     */
    public void setBaseURL(URL baseURL) {
        this.baseURL = baseURL;
    }

    /**
     * @return the deviceType
     */
    public String getType() {
        return type;
    }

    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(String type) {
        this.type = type;
    }

    /**
     * @return the friendlyName
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * @param friendlyName the friendlyName to set
     */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the manufacturerURL
     */
    public URL getManufacturerURL() {
        return manufacturerURL;
    }

    /**
     * @param manufacturerURL the manufacturerURL to set
     */
    public void setManufacturerURL(URL manufacturerURL) {
        this.manufacturerURL = manufacturerURL;
    }

    /**
     * @return the modelDescription
     */
    public String getModelDescription() {
        return modelDescription;
    }

    /**
     * @param modelDescription the modelDescription to set
     */
    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * @return the modelNumber
     */
    public String getModelNumber() {
        return modelNumber;
    }

    /**
     * @param modelNumber the modelNumber to set
     */
    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    /**
     * @return the modelURL
     */
    public URL getModelURL() {
        return modelURL;
    }

    /**
     * @param modelURL the modelURL to set
     */
    public void setModelURL(URL modelURL) {
        this.modelURL = modelURL;
    }

    /**
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @return the Unique Device Name
     */
    public String getUniqueDeviceName() {
        return uniqueDeviceName;
    }

    /**
     * @param uniqueDeviceName the Unique Device Name to set
     */
    public void setUniqueDeviceName(String uniqueDeviceName) {
        this.uniqueDeviceName = uniqueDeviceName;
    }

    /**
     * @return the Universal Product Code
     */
    public String getUniversalProductCode() {
        return universalProductCode;
    }

    /**
     * @param universalProductCode the Universal Product Code to set
     */
    public void setUniversalProductCode(String universalProductCode) {
        this.universalProductCode = universalProductCode;
    }

    /**
     * @return the presentationURL
     */
    public URL getPresentationURL() {
        return presentationURL;
    }

    /**
     * @param presentationURL the presentationURL to set
     */
    public void setPresentationURL(URL presentationURL) {
        this.presentationURL = presentationURL;
    }

    /**
     * @return the serviceList
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

//    public void addEmbeddedDevice(Device d){
//        embeddedDevices.add(d);
//    }
    
    public URL getDescriptionUrl() {
        return descriptionUrl;
    }

    public void setDescriptionUrl(URL descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
//    public Iterable<Device> getEmbeddedDevices() {
//        return embeddedDevices;
//    }
}
