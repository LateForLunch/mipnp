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
 * UpnpTools.java
 * Created on Jul 10, 2011, 1:46:46 PM
 */
package com.googlecode.mipnp.upnp;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class UpnpTools {

    /**
     * Returns the type of a service as a Uniform Resource Name (URN).<br/>
     * <br/>
     * Format:<br/>
     * "urn:schemas-upnp-org:service:<i>serviceType</i>:<i>ver</i>"<br/>
     * or<br/>
     * "urn:<i>domain-name</i>:service:<i>serviceType</i>:<i>ver</i>"
     * @param service The service to get the type from.
     * @return the type of the given service as a Uniform Resource Name (URN)
     */
//    public static String getTypeAsUrn(Service service) {
//        return getTypeAsUrn("service", service.getVendorDomainName(),
//                service.getType(), service.getVersion());
//    }

    /**
     * Returns the type of a device as a Uniform Resource Name (URN).<br/>
     * <br/>
     * Format:<br/>
     * "urn:schemas-upnp-org:device:<i>deviceType</i>:<i>ver</i>"<br/>
     * or<br/>
     * "urn:<i>domain-name</i>:device:<i>deviceType</i>:<i>ver</i>"
     * @param device The device to get the type from.
     * @return the type of the given device as a Uniform Resource Name (URN)
     */
//    public static String getTypeAsUrn(Device device) {
//        return getTypeAsUrn("device", device.getVendorDomainName(),
//                device.getType(), device.getVersion());
//    }

    /**
     * Returns the identifier of a service as a Uniform Resource Name (URN).<br/>
     * <br/>
     * Format:<br/>
     * "urn:upnp-org:serviceId:<i>serviceId</i>"<br/>
     * or<br/>
     * "urn:<i>domain-name</i>:serviceId:<i>serviceId</i>"
     * @param service The service to get the identifier from.
     * @return the identifier of the given service as a Uniform Resource Name (URN)
     */
//    public static String getIdAsUrn(Service service) {
//        String urn = "urn:";
//
//        String vendor = service.getVendorDomainName();
//        if (vendor.startsWith("schemas-")) {
//            vendor = vendor.substring(8);
//        }
//        urn += vendor;
//
//        urn += ":serviceId:" + service.getId();
//        return urn;
//    }

    /**
     * Generic method to create the type of a device or service as a Uniform Resource Name (URN).
     * @param urnType "device" or "service"
     * @param vendor The vendor domain name.
     * @param type The type of the device or service.
     * @param version The version of the device or service.
     * @return the type of the device or service as a Uniform Resource Name (URN)
     */
    private static String getTypeAsUrn(String urnType, String vendor,
            String type, int version) {

        String urn = "urn:";
        urn += vendor;
        urn += ":" + urnType + ":" + type + ":" + version;
        return urn;
    }
}
