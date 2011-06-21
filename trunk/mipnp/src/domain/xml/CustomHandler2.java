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

import domain.upnp.AbstractDeviceImpl;
import java.io.CharArrayWriter;
import java.net.MalformedURLException;
import java.net.URL;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Jeroen De Wilde
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
//public class CustomHandler2 extends DefaultHandler {
//
//    private XMLReader xmlReader;
//    private CharArrayWriter buffer;
//    private DeviceHandler deviceHandler;
//    private ServiceHandler serviceHandler;
//    private Device rootDev;
//    private Device currentDev;
//    private boolean inDevList;
//    private URL urlBase;
//
//    public CustomHandler2(XMLReader xmlReader) {
//        this.xmlReader = xmlReader;
//        this.buffer = new CharArrayWriter();
//        this.deviceHandler = new DeviceHandler();
//        this.rootDev = new Device();
//        this.currentDev = rootDev;
//        this.inDevList = false;
//    }
//
//    /**
//     * invoked when a start xml tag is parsed
//     * @param uri
//     * @param localName
//     * @param qName
//     * @param attributes
//     * @throws SAXException
//     */
//    @Override
//    public void startElement(
//            String uri, String localName,
//            String qName, Attributes attributes)
//            throws SAXException {
//
//        buffer.reset();
//
//        if (qName.equalsIgnoreCase("device")) {
//            Device dev = rootDev;
//            rootDev.setBaseURL(urlBase);
//            if (inDevList) {
//                dev = new Device();
////                    rootDev.addEmbeddedDevice(dev); // TODO (low priority) add support for embedded devices
//                currentDev = dev;
//            }
//            deviceHandler.handleDevice(dev, xmlReader, this);
//        } else if (qName.equalsIgnoreCase("deviceList")) {
//            this.inDevList = true;
//        }
//    }
//
//    /**
//     * invoked when a closing xml tag is parsed
//     * @param uri
//     * @param localName
//     * @param qName
//     * @throws SAXException
//     */
//    @Override
//    public void endElement(String uri, String localName, String qName)
//            throws SAXException {
//
//        if (localName.equalsIgnoreCase("deviceList")) {
//            this.inDevList = false;
//        } else if (qName.equalsIgnoreCase("URLBase")){ //Jeroen
//            try {
//                urlBase = new URL(buffer.toString());               
//            } catch (MalformedURLException ex) {
//                System.out.println("Malformed URL!");
//                ex.printStackTrace();;
//                System.exit(1);
//            }
//        }
//    }
//
//    /**
//     * invoked when between start and end XML tags
//     * @param ch
//     * @param start
//     * @param length
//     * @throws SAXException
//     */
//    @Override
//    public void characters(char[] ch, int start, int length)
//            throws SAXException {
//
//        buffer.write(ch, start, length);
//    }
//
//    public Device getRootDevice() {
//        return rootDev;
//    }
//}
