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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class DescriptionServlet extends HttpServlet {

    private IRootDevice rootDevice;

    public DescriptionServlet(IRootDevice rootDevice) {
        this.rootDevice = rootDevice;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/xml;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        try {
            out.println("<?xml version=\"1.0\"?>");
            out.println("<root xmlns=\"urn:schemas-upnp-org:device-1-0\">");
            out.println("<specVersion>");
            out.println("<major>" + rootDevice.getMajorVersion() + "</major>");
            out.println("<minor>" + rootDevice.getMinorVersion() + "</minor>");
            out.println("</specVersion>");
            out.println("<device>");
            out.println("<deviceType>" +
                    rootDevice.getUniformResourceName() + "</deviceType>");
            out.println("<friendlyName>" +
                    rootDevice.getFriendlyName() + "</friendlyName>");
            if (rootDevice.getManufacturer() != null) {
                out.println("<manufacturer>" +
                        rootDevice.getManufacturer() + "</manufacturer>");
            }
            if (rootDevice.getManufacturerUrl() != null) {
                out.println("<manufacturerURL>" +
                        rootDevice.getManufacturerUrl() + "</manufacturerURL>");
            }
            if (rootDevice.getModelDescription() != null) {
                out.println("<modelDescription>" +
                        rootDevice.getModelDescription() + "</modelDescription>");
            }
            out.println("<modelName>" +
                    rootDevice.getModelName() + "</modelName>");
            if (rootDevice.getModelNumber() != null) {
                out.println("<modelNumber>" +
                        rootDevice.getModelNumber() + "</modelNumber>");
            }
            if (rootDevice.getModelUrl() != null) {
                out.println("<modelURL>" +
                        rootDevice.getModelUrl() + "</modelURL>");
            }
            if (rootDevice.getSerialNumber() != null) {
                out.println("<serialNumber>" +
                        rootDevice.getSerialNumber() + "</serialNumber>");
            }
            out.println("<UDN>" +
                    "uuid:" + rootDevice.getUuid() + "</UDN>");
            for (IService service : rootDevice.getServices()) {
                // TODO
            }
            if (rootDevice.getPresentationUrl() != null) {
                out.println("<presentationURL>" +
                        rootDevice.getPresentationUrl() + "</presentationURL>");
            }
            out.println("</device>");
            out.println("</root>");
        } finally {            
            out.close();
        }
    }
}
