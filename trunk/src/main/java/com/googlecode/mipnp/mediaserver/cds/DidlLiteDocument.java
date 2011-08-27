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

import java.net.MalformedURLException;
import java.net.URL;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

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

    private URL mediaLocation;
    private Document document;

    public DidlLiteDocument(URL mediaLocation) {
        this.mediaLocation = mediaLocation;
        Element root = new Element("DIDL-Lite", NS_DEFAULT);
        root.addNamespaceDeclaration(NS_DC);
        root.addNamespaceDeclaration(NS_UPNP);
        this.document = new Document(root);
    }

    public void addCdsObject(CdsObject obj, String filter) {
        Element element = null;
        if (obj.isContainer()) {
            element = new Element("container", NS_DEFAULT);
            element.setAttribute(CdsConstants.PROPERTY_SEARCHABLE, "true");
        } else {
            element = new Element("item", NS_DEFAULT);
        }

        element.setAttribute(CdsConstants.PROPERTY_ID, obj.getId());
        String parentId = "-1";
        if (obj.getParent() != null) {
            parentId = obj.getParent().getId();
        }
        element.setAttribute(CdsConstants.PROPERTY_PARENT_ID, parentId);
        element.setAttribute(CdsConstants.PROPERTY_RESTRICTED, "true");

        Element upnpClass = new Element(
                removePrefix(CdsConstants.PROPERTY_CLASS),
                getNamespace(CdsConstants.PROPERTY_CLASS));
        upnpClass.setText(obj.getUpnpClass());
        element.addContent(upnpClass);

        Element title = new Element(
                removePrefix(CdsConstants.PROPERTY_TITLE),
                getNamespace(CdsConstants.PROPERTY_TITLE));
        title.setText(obj.getTitle());
        element.addContent(title);

        // TODO: temp fix, res location should be added to CdsObject as a property
        if (filter.equals("*") || filter.contains("res")) {
            Element resEl = new Element(CdsConstants.PROPERTY_RES, NS_DEFAULT);
            resEl.setText(mediaLocation.toString() + "/" + obj.getId());
            element.addContent(resEl);
        }

        if (filter.equals("*")) {
            for (String property : obj.getProperties()) {
                addProperty(element, obj, property);
            }
        } else {
            String[] filterParts = filter.split("(?<!\\\\),");
            for (int i = 0; i < filterParts.length; i++) {
                filterParts[i] = filterParts[i].replaceAll("\\\\,", ",");
                addProperty(element, obj, filterParts[i]);
            }
        }

        document.getRootElement().addContent(element);
    }

    @Override
    public String toString() {
//        Format format = Format.getRawFormat();
        Format format = Format.getPrettyFormat();
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
        String propertyValue = obj.getProperty(property);
        String strParentElement = property;
        if (strParentElement.contains("@")) {
            strParentElement =
                    strParentElement.substring(0, strParentElement.indexOf('@'));
        }
        Element parentElement = element.getChild(
                removePrefix(strParentElement), getNamespace(strParentElement));
        if (parentElement == null) {
            parentElement = new Element(
                    removePrefix(strParentElement),
                    getNamespace(strParentElement));
            parentElement.setText(obj.getProperty(strParentElement));
            element.addContent(parentElement);
        }
        if (property.contains("@")) {
            parentElement.setAttribute(
                    property.substring(property.indexOf('@') + 1),
                    propertyValue);
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        DidlLiteDocument doc = new DidlLiteDocument(new URL("http://localhost:9090"));
        CdsObject obj = new CdsObject(
                CdsConstants.UPNP_CLASS_STORAGE_FOLDER, "1", "testTitle");
        obj.setProperty("upnp:artist@role", "someRole");
        doc.addCdsObject(obj, "upnp:artist@role");
        System.out.println(doc.toString());
    }
}
