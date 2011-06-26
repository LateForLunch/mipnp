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
 * DescriptionServlet.java
 * Created on Jun 26, 2011, 2:46:48 PM
 */
package com.googlecode.mipnp.upnp.description;

import com.googlecode.mipnp.upnp.IRootDevice;
import com.googlecode.mipnp.upnp.IService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class DescriptionServlet extends AbstractHandler {

    private static final String FILE = "/description.xml";

    private IRootDevice rootDevice;

    public DescriptionServlet(IRootDevice rootDevice) {
        this.rootDevice = rootDevice;
    }

    public void handle(String target, Request baseRequest,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (!request.getRequestURI().equalsIgnoreCase(FILE)) {
            return;
        }
        response.setContentType("text/xml;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        PrintWriter out = response.getWriter();
        try {
            out.println("<?xml version=\"1.0\"?>");
            out.println("<root xmlns=\"urn:schemas-upnp-org:device-1-0\">");
            out.println("<specVersion>");
            out.println(formatTag("major", "" + rootDevice.getMajorVersion()));
            out.println(formatTag("minor", "" + rootDevice.getMinorVersion()));
            out.println("</specVersion>");
            out.println("<device>");
            out.println(formatTag("deviceType",
                    rootDevice.getUniformResourceName()));
            out.println(formatTag("friendlyName",
                    rootDevice.getFriendlyName()));
            out.println(formatTag("manufacturer",
                    rootDevice.getManufacturer(), true));
            out.println(formatTag("manufacturerURL",
                    rootDevice.getManufacturerUrl().toString(), true));
            out.println(formatTag("modelDescription",
                    rootDevice.getModelDescription(), true));
            out.println(formatTag("modelName",
                    rootDevice.getModelName()));
            out.println(formatTag("modelNumber",
                    rootDevice.getModelNumber(), true));
            out.println(formatTag("modelURL",
                    rootDevice.getModelUrl().toString(), true));
            out.println(formatTag("serialNumber",
                    rootDevice.getSerialNumber(), true));
            out.println(formatTag("UDN",
                    "uuid:" + rootDevice.getUuid()));
            for (IService service : rootDevice.getServices()) {
                // TODO
            }
            out.println(formatTag("presentationURL",
                    rootDevice.getPresentationUrl().toString(), true));
            out.println("</device>");
            out.println("</root>");
        } finally {            
            out.close();
        }
    }

    private String formatTag(String tag, String value) {
        return formatTag(tag, value, false);
    }

    private String formatTag(String tag, String value, boolean optional) {
        if (optional && (value == null || value.isEmpty())) {
            return "";
        }
        if (value == null || value.isEmpty()) {
            return "<" + tag + "/>";
        }
        return "<" + tag + ">" + value + "</" + tag + ">";
    }
}
