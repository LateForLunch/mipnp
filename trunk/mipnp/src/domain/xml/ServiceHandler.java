/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jeroen De Wilde, Jochem Van denbussche
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
package domain.xml;

import domain.upnp.Service;
import domain.upnp.services.ServiceFactory;
import java.io.CharArrayWriter;
import java.net.MalformedURLException;
import java.net.URL;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Jeroen De Wilde
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class ServiceHandler extends DefaultHandler {

    private CharArrayWriter buffer;
    private XMLReader xmlReader;
    private ContentHandler parent;
    private Service currentServ;
    //Jeroen
    private String urlBase;

    public ServiceHandler() {
        this.buffer = new CharArrayWriter();
    }

    /**
     * handles the service with the correct handler
     * @param newServ
     * @param xmlReader
     * @param parent
     */
    public void handleService(
            Service newServ, XMLReader xmlReader, ContentHandler parent) {

        this.currentServ = newServ;
        this.xmlReader = xmlReader;
        this.parent = parent;
        urlBase = ((DeviceHandler) parent).getCurrentDev().getBaseURL().toString();
        xmlReader.setContentHandler(this);

    }

    /**
     * invoked when a start xml tag is parsed
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(
            String uri, String localName,
            String qName, Attributes attributes)
            throws SAXException {

        buffer.reset();
    }

    /**
     * invoked when a closing xml tag is parsed
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        try {
            if (qName.equalsIgnoreCase("serviceType")) {
                currentServ.setServiceType(buffer.toString());
            } else if (qName.equalsIgnoreCase("serviceId")) {
                String currentServiceId = buffer.toString();
                //check which service is being parsed + initialize correct service
                if (currentServiceId.equalsIgnoreCase("urn:upnp-org:serviceId:ContentDirectory")) {
                    ServiceFactory.initializeService(currentServ, "contentdirectory");
                } else if (currentServiceId.equalsIgnoreCase("urn:upnp-org:serviceId:ConnectionManager")) {
                    ServiceFactory.initializeService(currentServ, "connectionmanager");
                } else {
                    throw new IllegalArgumentException(currentServiceId + " not yet implemented!");
                }
                currentServ.setServiceId(currentServiceId);
            } else if (qName.equalsIgnoreCase("SCPDURL")) {
                currentServ.setDescriptionURL(new URL(urlBase.concat(buffer.toString())));
            } else if (qName.equalsIgnoreCase("controlURL")) {
                currentServ.setControlURL(new URL(urlBase.concat(buffer.toString())));
            } else if (qName.equalsIgnoreCase("eventSubURL")) {
                currentServ.setEventSubURL(new URL(urlBase.concat(buffer.toString())));
            } else if (qName.equalsIgnoreCase("service")) {
                xmlReader.setContentHandler(parent);
            }

        } catch (MalformedURLException ex) {
            System.out.println("malformed URL");
            ex.printStackTrace();
        }
    }

    /**
     * invoked when between start and end XML tags
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        buffer.write(ch, start, length);
    }
}
