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

package domain;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author nicholaihel
 */

/*
 * <?xml version="1.0"?>
<root xmlns="urn:schemas-upnp-org:device-1-0">
	<specVersion>
		<major>1</major>
		<minor>0</minor>
	</specVersion>
	<URLBase>URLBase</URLBase>
	<device>
		<deviceType> urn:schemas-upnp-org:device:MediaServer:1</deviceType>
		<friendlyName>MiPnP v0.1</friendlyName>
		<manufacturer>MiPnP devs</manufacturer>
		<manufacturerURL>https://code.google.com/p/mipnp/</manufacturerURL>
		<modelDescription>MiPnP: Minimal UPnP MediaServer</modelDescription>
		<modelName>Modelname: MiPnP xxx</modelName>
		<modelNumber>01</modelNumber>
		<modelURL>https://code.google.com/p/mipnp/</modelURL>
		<serialNumber>serialNumber</serialNumber>
		<UDN>uuid:UUID</UDN>
		<UPC>Universal Product Code</UPC>
		<serviceList>
			<service>
				<serviceType>urn:schemas-upnp-org:service:ContentDirectory:1 </serviceType>
				<serviceId>urn:upnp-org:serviceId:ContentDirectory </serviceId>
				<SCPDURL>URL to service description</SCPDURL>
				<controlURL>URL for control</controlURL>
				<eventSubURL>URL for eventing</eventSubURL>
			</service>
			<service>
				<serviceType>urn:schemas-upnp-org:service:ConnectionManager:1</serviceType>
				<serviceId>urn:upnp-org:serviceId:ConnectionManager</serviceId>
				<SCPDURL>URL to service description</SCPDURL>
				<controlURL>URL for control</controlURL>
				<eventSubURL>URL for eventing</eventSubURL>
			</service>
			</serviceList>
		<deviceList> Description of embedded devices added by UPnP vendor (if any) go here </deviceList>
		<presentationURL>URL for presentation</presentationURL>
	</device>
</root>

 */
public class Device {
    private URL urlBase;

    private String deviceType;
    private String friendlyName;
    private String manufacturer;
    private URL manufacturerURL;
    private String modelDescription;
    private String modelName;
    private int modelNumber; //check if int
    private URL modelURL;
    private String serialNumber; //check if string/int/..
    private String udn; //TODO: udn gewoon als string genomen, want wordt meegeven door XML
                        //UUID heeft geen constructor (om deze bv uit string over te nemen)
    private String upc;

    private URL presentationURL;

    private List<Service> services;
    private List<Device> embeddedDevices; //TODO: NOG GEEN GETTERS EN SETTERS VOORZIEN

    public Device(){
        services = new ArrayList<Service>();
        embeddedDevices = new ArrayList<Device>();
    }

    public void addService(Service s){
        if(!services.contains(s))
            getServices().add(s);
    }

    /**
     * @return the urlBase
     */
    public URL getUrlBase() {
        return urlBase;
    }

    /**
     * @param urlBase the urlBase to set
     */
    public void setUrlBase(URL urlBase) {
        this.urlBase = urlBase;
    }

    /**
     * @return the deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
    public int getModelNumber() {
        return modelNumber;
    }

    /**
     * @param modelNumber the modelNumber to set
     */
    public void setModelNumber(int modelNumber) {
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
     * @return the udn
     */
    public String getUdn() {
        return udn;
    }

    /**
     * @param udn the udn to set
     */
    
    public void setUdn(String udn) {
        this.udn = udn;
    }

    /**
     * @return the upc
     */
    public String getUpc() {
        return upc;
    }

    /**
     * @param upc the upc to set
     */
    public void setUpc(String upc) {
        this.upc = upc;
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

    public void addEmbeddedDevice(Device d){
        embeddedDevices.add(d);
    }


}

