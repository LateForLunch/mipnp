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
 * DidlLiteDocument.java
 * Created on Aug 26, 2011, 5:12:46 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class DidlLiteDocument {

    private static final Namespace NS_DEFAULT =
            Namespace.getNamespace("urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/");
    private static final Namespace NS_DC =
            Namespace.getNamespace("dc", "http://purl.org/dc/elements/1.1/");
    private static final Namespace NS_UPNP =
            Namespace.getNamespace("upnp", "urn:schemas-upnp-org:metadata-1-0/upnp/");

    private static final List<String> REQUIRED_PROPERTIES = Arrays.asList(
            CdsConstants.PROPERTY_ID,
            CdsConstants.PROPERTY_PARENT_ID,
            CdsConstants.PROPERTY_TITLE,
            CdsConstants.PROPERTY_CLASS,
            CdsConstants.PROPERTY_RESTRICTED
    );

    private URL mediaLocation;
    private String filter;
    private Document document;

    public DidlLiteDocument(URL mediaLocation, String filter) {
        this.mediaLocation = mediaLocation;
        this.filter = filter;
        Element root = new Element("DIDL-Lite", NS_DEFAULT);
        root.addNamespaceDeclaration(NS_DC);
        root.addNamespaceDeclaration(NS_UPNP);
        this.document = new Document(root);
    }

    public void addCdsObject(CdsObject obj) {
        Element element = null;
        if (obj.isContainer()) {
            element = new Element("container", NS_DEFAULT);
            addProperty(element, obj, CdsConstants.PROPERTY_SEARCHABLE, "true");
        } else {
            element = new Element("item", NS_DEFAULT);
        }

        for (String reqProp : REQUIRED_PROPERTIES) {
            addProperty(element, obj, reqProp);
        }

        // TODO: temp fix, res location should be added to CdsObject as a property
        Resource res = obj.getResource();
        if (res != null && (filter.equals("*") || filter.contains("res"))) {
            Element resEl = new Element(CdsConstants.PROPERTY_RES, NS_DEFAULT);
            resEl.setAttribute(
                    "protocolInfo", "http-get:*:" + res.getMimeType() + ":*");
            resEl.setText(mediaLocation.toString() + "/" + obj.getId());
            element.addContent(resEl);
        }
        // END temp fix

        if (filter.equals("*")) {
            for (String property : obj.getProperties()) {
                if (!REQUIRED_PROPERTIES.contains(property)) {
                    addProperty(element, obj, property);
                }
            }
        } else {
            String[] filterParts = filter.split("(?<!\\\\),");
            for (int i = 0; i < filterParts.length; i++) {
                filterParts[i] = filterParts[i].replaceAll("\\\\,", ",");
                if (!REQUIRED_PROPERTIES.contains(filterParts[i])) {
                    addProperty(element, obj, filterParts[i]);
                }
            }
        }

        document.getRootElement().addContent(element);
    }

    @Override
    public String toString() {
        Format format = Format.getRawFormat();
        format.setOmitDeclaration(true);
        XMLOutputter outputter = new XMLOutputter(format);
        return outputter.outputString(document);
    }

    private String removePrefix(String property) {
        return property.substring(property.indexOf(':') + 1);
    }

    private Namespace getNamespace(String property) {
        if (property.startsWith("dc:")) {
            return NS_DC;
        } else if (property.startsWith("upnp:")) {
            return NS_UPNP;
        } else {
            return NS_DEFAULT;
        }
    }

    private void addProperty(Element element, CdsObject obj, String property) {
        if (!obj.containsProperty(property)) {
            return;
        }
        addProperty(element, obj, property, obj.getProperty(property));
    }

    private void addProperty(
            Element element, CdsObject obj,
            String property, String propertyValue) {

        if (property.contains("@")) {
            Element parentElement = null;
            String strParentElement = property.substring(0, property.indexOf('@'));
            if (strParentElement.equals("")) {
                parentElement = element;
            } else {
                parentElement = element.getChild(
                        removePrefix(strParentElement),
                        getNamespace(strParentElement));
                if (parentElement == null) {
                    parentElement = new Element(
                            removePrefix(strParentElement),
                            getNamespace(strParentElement));
                    parentElement.setText(obj.getProperty(strParentElement));
                    element.addContent(parentElement);
                }
            }
            parentElement.setAttribute(
                    property.substring(property.indexOf('@') + 1),
                    propertyValue);
        } else {
            Element newElement = new Element(
                    removePrefix(property),
                    getNamespace(property));
            newElement.setText(propertyValue);
            element.addContent(newElement);
        }
    }
}
