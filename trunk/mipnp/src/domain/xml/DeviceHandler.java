/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.xml;

import domain.upnp.Device;
import domain.upnp.Service;
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
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 *
 */
class DeviceHandler extends DefaultHandler {

        private CharArrayWriter buffer;
        private XMLReader xmlReader;
        private ContentHandler parent;
        private Device currentDev;
        private ServiceHandler serviceHandler;

        public DeviceHandler() {
            this.buffer = new CharArrayWriter();
        }
        /**
         * handles the device with the correct handler
         * @param newDev
         * @param xmlReader
         * @param parent
         */
        public void handleDevice(
                Device newDev, XMLReader xmlReader, ContentHandler parent) {

            this.currentDev = newDev;
            this.xmlReader = xmlReader;
            this.parent = parent;
            xmlReader.setContentHandler(this);
            serviceHandler = new ServiceHandler();
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

            if (qName.equalsIgnoreCase("service")) {
                Service newServ = new Service();
                currentDev.addService(newServ);
                serviceHandler.handleService(newServ, xmlReader, this);
            }

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
        try{
            if (qName.equalsIgnoreCase("deviceType")) {
                currentDev.setDeviceType(buffer.toString());
            } else if (qName.equalsIgnoreCase("friendlyName")) {
                currentDev.setFriendlyName(buffer.toString());
            } else if (qName.equalsIgnoreCase("manufacturer")) {
                currentDev.setManufacturer(buffer.toString());
            } else if (qName.equalsIgnoreCase("manufacturerURL")) {
                currentDev.setManufacturerURL(new URL(buffer.toString()));
            } else if(qName.equalsIgnoreCase("modelDescription")) {
                currentDev.setModelDescription(buffer.toString());
            } else if(qName.equalsIgnoreCase("modelName")) {
                currentDev.setModelName(buffer.toString());
            } else if(qName.equalsIgnoreCase("modelNumber")) {
                currentDev.setModelNumber(Integer.parseInt(buffer.toString()));
            } else if(qName.equalsIgnoreCase("modelURL")) {
                currentDev.setModelURL(new URL(buffer.toString()));
            } else if(qName.equalsIgnoreCase("serialNumber")) {
                currentDev.setSerialNumber(buffer.toString());
            } else if(qName.equalsIgnoreCase("UDN")) {
                currentDev.setUdn(buffer.toString());
            } else if(qName.equalsIgnoreCase("UPC")) {
                currentDev.setUpc(buffer.toString());
            } else if(qName.equalsIgnoreCase("presentationURL")) {
                currentDev.setPresentationURL(new URL(buffer.toString()));
            } else if(qName.equalsIgnoreCase("urlBase")) {
                currentDev.setUrlBase(new URL(buffer.toString()));
            } else if (qName.equalsIgnoreCase("device")) {
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
