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
 * MediaReceiverRegistrar.java
 * Created on Jun 30, 2011, 4:00:45 PM
 */
package com.googlecode.mipnp.mediaserver;

import com.googlecode.mipnp.upnp.ServiceImpl;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Holder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@WebService(
        portName="X_MS_MediaReceiverRegistrar",
        targetNamespace=MediaReceiverRegistrar.TYPE_AS_URN)
public class MediaReceiverRegistrar extends ServiceImpl {

    public static final String TYPE_AS_URN =
            "urn:microsoft.com:service:X_MS_MediaReceiverRegistrar:1";

    public static final String ID_AS_URN =
            "urn:microsoft.com:serviceId:X_MS_MediaReceiverRegistrar";

    private static final String XML_DESCRIPTION =
            "/mediaserver/X_MS_MediaReceiverRegistrar-1.xml";

    public MediaReceiverRegistrar() {
//        super(SERVICE_VENDOR_DOMAIN_NAME, SERVICE_TYPE,
//                SERVICE_ID, SERVICE_VERSION);
        super(TYPE_AS_URN, ID_AS_URN);
        // TODO: check urn -> urn:microsoft.com should be urn:microsoft-com
//        setUniformResourceName("urn:microsoft.com:service:X_MS_MediaReceiverRegistrar:1");
//        setIdentifier("urn:microsoft.com:serviceId:X_MS_MediaReceiverRegistrar");
        try {
//            parseDescription(new File(XML_SERVICE_DESCRIPTION));
            parseDescription(
                    getClass().getResourceAsStream(XML_DESCRIPTION));
        } catch (Exception ex) {
            // This should not happen
            ex.printStackTrace(); // TODO: remove line if everything seems alright
        }
    }

    @WebMethod(
            operationName="IsAuthorized",
            action=TYPE_AS_URN + "#IsAuthorized")
    public void isAuthorized(
            @WebParam(name="DeviceID")
            String deviceId,
            @WebParam(name="Result", mode=WebParam.Mode.OUT)
            Holder<Integer> result) {

        result.value = 1;
    }

    @WebMethod(
            operationName="RegisterDevice",
            action=TYPE_AS_URN + "#RegisterDevice")
    public void registerDevice(
            @WebParam(name="RegistrationReqMsg")
            byte[] registrationReqMsg,
            @WebParam(name="RegistrationRespMsg", mode=WebParam.Mode.OUT)
            Holder<byte[]> registrationRespMsg) {

        System.out.println("TODO: implement MediaReceiverRegistrar.registerDevice"); // TODO
    }

    @WebMethod(
            operationName="IsValidated",
            action=TYPE_AS_URN + "#IsValidated")
    public void isValidated(
            @WebParam(name="DeviceID")
            String deviceId,
            @WebParam(name="Result", mode=WebParam.Mode.OUT)
            Holder<Integer> result) {

        result.value = 1;
    }

    /**
     * Overridden because the '.' characters in the vendor domain name
     * may not be replaced with '-'.
     * @return the type of the service as a URN
     */
    @Override
    public String getTypeAsUrn() {
        String urn = "urn:";
        urn += getVendorDomainName();
        urn += ":service:" + getType() + ":" + getVersion();
        return urn;
    }

    /**
     * Overridden because the '.' characters in the vendor domain name
     * may not be replaced with '-'.
     * @return the identifier of the service as a URN
     */
    @Override
    public String getIdAsUrn() {
        String urn = "urn:";
        urn += getVendorDomainName();
        urn += ":serviceId:" + getId();
        return urn;
    }
}
