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
 * ServiceDescriptionServlet.java
 * Created on Jul 10, 2011, 3:21:16 PM
 */
package com.googlecode.mipnp.upnp.description;

import com.googlecode.mipnp.upnp.Action;
import com.googlecode.mipnp.upnp.Parameter;
import com.googlecode.mipnp.upnp.RootDevice;
import com.googlecode.mipnp.upnp.Service;
import com.googlecode.mipnp.upnp.StateVariable;
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
public class ServiceDescriptionServlet extends HttpServlet {

    private RootDevice rootDevice;
    private Service service;

    public ServiceDescriptionServlet(RootDevice rootDevice, Service service) {
        this.rootDevice = rootDevice;
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/xml;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        try {
            out.println("<?xml version=\"1.0\"?>");
            out.println("<scpd xmlns=\"urn:schemas-upnp-org:service-1-0\" "
                    + "configId=\"" + rootDevice.getConfigId() + "\">");
            out.println("<specVersion>");
            out.println("<major>" + rootDevice.getMajorUpnpVersion() + "</major>");
            out.println("<minor>" + rootDevice.getMinorUpnpVersion() + "</minor>");
            out.println("</specVersion>");
            if (service.getActions().size() > 0) {
                out.println("<actionList>");
                for (Action action : service.getActions()) {
                    out.println("<action>");
                    out.println("<name>" + action.getName() + "</name>");
                    // TODO: argumentList
                    if (action.getParameters().size() > 0) {
                        out.println("<argumentList>");
                        for (Parameter param : action.getParameters()) {
                            out.println("<argument>");
                            out.println("<name>" + param.getName() + "</name>");
                            out.println("<direction>" +
                                    param.getDirection() + "</direction>");
                            out.println("<relatedStateVariable>" +
                                    param.getRelatedStateVariable().getName() +
                                    "</relatedStateVariable>");
                            out.println("</argument>");
                        }
                        out.println("</argumentList>");
                    }
                    out.println("</action>");
                }
                out.println("</actionList>");
            }
            out.println("<serviceStateTable>");
            for (StateVariable var : service.getStateVariables()) {
                String sendEvents = var.hasToSendEvents() ? "yes" : "no";
                String multicast = var.hasToMulticast() ? "yes" : "no";
                out.println("<stateVariable "
                        + "sendEvents=\"" + sendEvents + "\" "
                        + "multicast=\""+ multicast + "\">");
                out.println("<name>" + var.getName() + "</name>");
                out.println("<dataType>" + var.getDataType() + "</dataType>");
                if (var.getDefaultValue() != null) {
                    out.println("<defaultValue>" +
                            var.getDefaultValue() + "</defaultValue>");
                }
                if (var.getAllowedValues() != null && var.getAllowedValues().size() > 0) {
                    out.println("<allowedValueList>");
                    for (String s : var.getAllowedValues()) {
                        out.println("<allowedValue>" + s + "</allowedValue>");
                    }
                    out.println("</allowedValueList>");
                }
                // TODO: allowedValueRange
                out.println("</stateVariable>");
            }
            out.println("</serviceStateTable>");
            out.println("</scpd>");
        } finally {
            out.close();
        }
    }
}
