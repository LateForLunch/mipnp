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

/**
 *
 * @author Jeroen De Wilde
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
//class DeviceHandler extends DefaultHandler {
//
//    private CharArrayWriter buffer;
//    private XMLReader xmlReader;
//    private ContentHandler parent;
//    private AbstractDeviceImpl currentDev;
//    private ServiceHandler serviceHandler;
//
//    public DeviceHandler() {
//        this.buffer = new CharArrayWriter();
//    }
//
//    /**
//     * handles the device with the correct handler
//     * @param newDev
//     * @param xmlReader
//     * @param parent
//     */
//    public void handleDevice(
//            AbstractDeviceImpl newDev, XMLReader xmlReader, ContentHandler parent) {
//
//        this.currentDev = newDev;
//        this.xmlReader = xmlReader;
//        this.parent = parent;
//        xmlReader.setContentHandler(this);
//        serviceHandler = new ServiceHandler();
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
//        if (qName.equalsIgnoreCase("service")) {
//            Service newServ = new Service();
//            getCurrentDev().addService(newServ);
//            serviceHandler.handleService(newServ, xmlReader, this);
//        }
//
//        buffer.reset();
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
//        try {
//            if (qName.equalsIgnoreCase("deviceType")) {
//                getCurrentDev().setDeviceType(buffer.toString());
//            } else if (qName.equalsIgnoreCase("friendlyName")) {
//                getCurrentDev().setFriendlyName(buffer.toString());
//            } else if (qName.equalsIgnoreCase("manufacturer")) {
//                getCurrentDev().setManufacturer(buffer.toString());
//            } else if (qName.equalsIgnoreCase("manufacturerURL")) {
//                getCurrentDev().setManufacturerURL(new URL(buffer.toString()));
//            } else if (qName.equalsIgnoreCase("modelDescription")) {
//                getCurrentDev().setModelDescription(buffer.toString());
//            } else if (qName.equalsIgnoreCase("modelName")) {
//                getCurrentDev().setModelName(buffer.toString());
//            } else if (qName.equalsIgnoreCase("modelNumber")) {
//                getCurrentDev().setModelNumber(buffer.toString());
//            } else if (qName.equalsIgnoreCase("modelURL")) {
//                getCurrentDev().setModelURL(new URL(buffer.toString()));
//            } else if (qName.equalsIgnoreCase("serialNumber")) {
//                getCurrentDev().setSerialNumber(buffer.toString());
//            } else if (qName.equalsIgnoreCase("UDN")) {
//                getCurrentDev().setUniqueDeviceName(buffer.toString());
//            } else if (qName.equalsIgnoreCase("UPC")) {
//                getCurrentDev().setUniversalProductCode(buffer.toString());
//            } else if (qName.equalsIgnoreCase("presentationURL")) {
//                getCurrentDev().setPresentationURL(new URL(getCurrentDev().getBaseURL().toString().concat(buffer.toString())));
//            } else if (qName.equalsIgnoreCase("urlBase")) {
//                getCurrentDev().setBaseURL(new URL(buffer.toString()));
//            } else if (qName.equalsIgnoreCase("device")) {
//                xmlReader.setContentHandler(parent);
//            }
//        } catch (MalformedURLException ex) {
//            System.out.println("malformed URL");
//            ex.printStackTrace();
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
//
//    }
//
//    /**
//     *
//     * @return the currentDev
//     */
//    public AbstractDeviceImpl getCurrentDev() {
//        return currentDev;
//    }
//}
