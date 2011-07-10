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
 * MediaReceiverRegistrar.java
 * Created on Jul 1, 2011, 2:50:48 PM
 */
package com.googlecode.mipnp.upnp.mediaserver;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Holder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@WebService(name="X_MS_MediaReceiverRegistrar")
public interface MediaReceiverRegistrar {

    @WebMethod(operationName="IsAuthorized")
    void isAuthorized(
            @WebParam(name="DeviceID")
            String deviceID,
            @WebParam(name="Result", mode=WebParam.Mode.OUT)
            Holder<Integer> result);

    @WebMethod(operationName="RegisterDevice")
    void registerDevice(
            @WebParam(name="RegistrationReqMsg")
            byte[] registrationReqMsg,
            @WebParam(name="RegistrationRespMsg", mode=WebParam.Mode.OUT)
            Holder<byte[]> registrationRespMsg);

    @WebMethod(operationName="IsValidated")
    void isValidated(
            @WebParam(name="DeviceID")
            String deviceID,
            @WebParam(name="Result", mode=WebParam.Mode.OUT)
            Holder<Integer> result);
}
