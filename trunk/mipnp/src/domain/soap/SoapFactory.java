/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Tijl Van Assche
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

package domain.soap;

import domain.http.HttpConstants;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Tijl Van Assche
 */
public class SoapFactory implements HttpConstants {

    public static SoapResponse createSoapResponse(SoapAction action) {

        // TODO get this info from Service
        String actionname = "";
        String serviceType = ""; // format: 'serviceType:v'
        HashMap<String, String> arguments = new HashMap<String, String>();
//        arguments.put("argumentName", "argumentValue");
        //

        String args = "";
        Iterator it = arguments.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            args = args + "<" + key + ">" + arguments.get(key) + "</" + key + ">";

        }

        String content = ""
                + "<?xml version=\"1.0\"?>"
                + "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\""
                + " s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
                + "<s:Body>"
                + " <u:"+actionname+"Response xmlns:u=\"urn:schemas-upnp-org:service:"+serviceType+"\">"
                +    args + ""
                + " </u:"+actionname+"Response>"
                + "</s:Body>"
                + "</s:Envelope>";

        SoapResponse response = new SoapResponse();
        response.setStatusCode(STATUS_OK);
        response.setVersion(HttpConstants.HTTP_VERSION_1_0); // of HTTP/1.1 (?)
        response.setHeader(HttpConstants.HEADER_CONTENT_TYPE, "text/xml; charset utf-8");
        response.setHeader(HttpConstants.HEADER_DATE, new SimpleDateFormat(HttpConstants.HTTP_DATE_PATTERN).format(new Date()));
        // TODO
        response.setHeader("SERVER", "OS/version UPnP/1.1 product/version");
        //
        try {
            response.setContent(content.getBytes("utf-8"), true);
        } catch (UnsupportedEncodingException ex) {
            response.setContent(content.getBytes(), true);
        }

        return response;
    }

    public static SoapAction createSoapAction() {
        // TODO
//        SoapAction action = new SoapAction(socket);
//        action.setHeader(HttpConstants.HOST, socket.getInetAddress()+":"+socket.getPort());
//
//        return action;
        return null;
    }
}
