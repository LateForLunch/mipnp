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
 * DeviceDescriptionServlet.java
 * Created on Jun 26, 2011, 2:46:48 PM
 */
package com.googlecode.mipnp.upnp.description;

import com.googlecode.mipnp.upnp.Device;
import com.googlecode.mipnp.upnp.RootDevice;
import com.googlecode.mipnp.upnp.Service;
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
public class DeviceDescriptionServlet extends HttpServlet {

    private RootDevice rootDevice;

    public DeviceDescriptionServlet(RootDevice rootDevice) {
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
            out.println("<major>" + rootDevice.getMajorUpnpVersion() + "</major>");
            out.println("<minor>" + rootDevice.getMinorUpnpVersion() + "</minor>");
            out.println("</specVersion>");
            printDevice(out, rootDevice);
            out.println("</root>");
        } finally {
            out.close();
        }
    }

    private void printDevice(PrintWriter out, Device device) {
        out.println("<device>");
        out.println("<deviceType>" +
                device.getTypeAsUrn() + "</deviceType>");
        out.println("<friendlyName>" +
                device.getFriendlyName() + "</friendlyName>");
        if (device.getManufacturer() != null) {
            out.println("<manufacturer>" +
                    device.getManufacturer() + "</manufacturer>");
        }
        if (device.getManufacturerUrl() != null) {
            out.println("<manufacturerURL>" +
                    device.getManufacturerUrl() + "</manufacturerURL>");
        }
        if (device.getModelDescription() != null) {
            out.println("<modelDescription>" +
                    device.getModelDescription() + "</modelDescription>");
        }
        out.println("<modelName>" +
                device.getModelName() + "</modelName>");
        if (device.getModelNumber() != null) {
            out.println("<modelNumber>" +
                    device.getModelNumber() + "</modelNumber>");
        }
        if (device.getModelUrl() != null) {
            out.println("<modelURL>" +
                    device.getModelUrl() + "</modelURL>");
        }
        if (device.getSerialNumber() != null) {
            out.println("<serialNumber>" +
                    device.getSerialNumber() + "</serialNumber>");
        }
        out.println("<UDN>" +
                "uuid:" + device.getUuid() + "</UDN>");
        if (device.getUniversalProductCode() != null) {
            out.println("<UPC>" +
                    device.getUniversalProductCode() + "</UPC>");
        }

        // Services
        if (device.getServices().size() > 0) {
            out.println("<serviceList>");
            for (Service service : device.getServices()) {
                printService(out, service);
            }
            out.println("</serviceList>");
        }

        // Embedded devices
        if (device.getEmbeddedDevices().size() > 0) {
            out.println("<deviceList>");
            for (Device embedded : device.getEmbeddedDevices()) {
                printDevice(out, embedded);
            }
            out.println("</deviceList>");
        }

        if (device.getPresentationUrl() != null) {
            out.println("<presentationURL>" +
                    device.getPresentationUrl() + "</presentationURL>");
        }
        out.println("</device>");
    }

    private void printService(PrintWriter out, Service service) {
        out.println("<service>");
        out.println("<serviceType>" +
                service.getTypeAsUrn() + "</serviceType>");
        out.println("<serviceId>" +
                service.getIdAsUrn() + "</serviceId>");
        out.println("<SCPDURL>" +
                service.getDescriptionUri() + "</SCPDURL>");
        out.println("<controlURL>" +
                service.getControlUri() + "</controlURL>");
        out.println("<eventSubURL>" +
                service.getEventUri() + "</eventSubURL>");
        out.println("</service>");
    }
}
