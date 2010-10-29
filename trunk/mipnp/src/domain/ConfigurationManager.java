/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jeroen De Wilde
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
 * ConfigurationManager.java
 * Created on Oct 20, 2010, 3:53:17 PM
 */
package domain;

import com.sun.org.apache.xerces.internal.parsers.XMLParser;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Jeroen De Wilde
 */
public class ConfigurationManager {

    private SAXParserFactory saxParserFactory;

    public /*static*/ Device createDeviceFromXML(String xmlFilePath) {
        try {
            //fetch HashMap generated from XML document
            HashMap<String, String> map = this.parseXMLToHashMap(xmlFilePath);

            //create Service 1 (ContentDirectory)
            Service service1 = new Service(map.get("serviceId1"), map.get("serviceType1"));
            service1.setScpdURL(new URL(map.get("SCPDURL1")));
            service1.setEventSubURL(new URL(map.get("eventSubURL1")));
            service1.setControlURL(new URL(map.get("controlURL1")));

            //create Service 2 (ConnectionManager)
            Service service2 = new Service(map.get("serviceId2"), map.get("serviceType2"));
            service2.setScpdURL(new URL(map.get("SCPDURL2")));
            service2.setEventSubURL(new URL(map.get("eventSubURL2")));
            service2.setControlURL(new URL(map.get("controlURL2")));

            //create Device + add services
            Device rootDevice = new Device();
            //add 2 services
            rootDevice.addService(service1);
            rootDevice.addService(service2);
            //set attributes
            rootDevice.setDeviceType(map.get("deviceType"));
            rootDevice.setFriendlyName(map.get("friendlyName"));
            rootDevice.setManufacturer(map.get("manufacturer"));
            rootDevice.setManufacturerURL(new URL(map.get("manufacturerURL")));
            rootDevice.setModelDescription(map.get("modelDescription"));
            rootDevice.setModelName(map.get("modelName"));
            rootDevice.setModelNumber(Integer.parseInt(map.get("modelNumber")));
            rootDevice.setModelURL(new URL(map.get("modelURL")));
            rootDevice.setPresentationURL(new URL(map.get("presentationURL")));
            rootDevice.setSerialNumber(map.get("serialNumber"));
            rootDevice.setUdn(map.get("UDN"));
            rootDevice.setUpc(map.get("UPC"));
            rootDevice.setUrlBase(new URL(map.get("URLBase")));

            return rootDevice;

        } catch (MalformedURLException ex) {
            System.out.println("Error by creating URL from input string");
            ex.printStackTrace();
            System.exit(1);
        } catch (NumberFormatException ex) {
            System.out.println("Error by parsing String to Integer");
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private HashMap<String, String> parseXMLToHashMap(String xmlFilePath) {
        try {
            //create File from XMLFilePath
            File xmlDoc = new File(xmlFilePath);

            //create SAXParserFactory
            saxParserFactory = SAXParserFactory.newInstance();

            //Parsing from XML document using SAX
            //TODO: opzoeken eventuele validatie
            saxParserFactory.setValidating(true); //TODO: opzoeken
            saxParserFactory.setNamespaceAware(false); //TODO: opzoeken

            SAXParser parser = saxParserFactory.newSAXParser();
            CustomSAXHandler handler = new CustomSAXHandler();
            parser.parse(xmlDoc, handler);

            if (!handler.xmlMap.isEmpty() /* && ... */) {
                return handler.xmlMap;
            } else; // throw new Exception
        } catch (IOException ex) {
            System.out.println("Error occured by parsing");
            ex.printStackTrace();
            System.exit(1);
        } catch (ParserConfigurationException ex) {
            System.out.println("Error occured by creating/configuring SAXParser");
            ex.printStackTrace();
            System.exit(1);
        } catch (SAXException ex) {
            System.out.println("Error occured by parsing in CustomSAXHandler");
            ex.printStackTrace();
            System.exit(1);
        }

        return null; //TODO: zou niet mogen maar netbeans zaagt erop.
    }

    static class CustomSAXHandler extends DefaultHandler {

        private HashMap<String, String> xmlMap;
        private int count = 0;
        private String key;

        public CustomSAXHandler() {
            xmlMap = new HashMap<String, String>();
        }

        @Override
        public void startDocument() throws SAXException {
            //System.out.println("XML parsing started");
        }

        @Override
        public void endDocument() throws SAXException {
            //System.out.println("XML parsing finished");
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                Attributes attrs) throws SAXException {
            //System.out.println("XML tag started");
            if (qName.toLowerCase().equals("service")) {
                count++;
            }

            if (qName.equals("serviceType")
                    || qName.equals("serviceId")
                    || qName.equals("SCPDURL")
                    || qName.equals("controlURL")
                    || qName.equals("eventSubURL")) {

                String temp = String.copyValueOf(qName.toCharArray());

                StringBuilder stb = new StringBuilder(temp).append(count);

                key = stb.toString();
            } else {
                key = qName;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            //System.out.println("XML tag finished");
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            //System.out.println("Inside XML tag");
            String xmlData = new String(ch).substring(start, start + length);
            int xmlDataHash = xmlData.hashCode();

            //check if hashcode equals 10 (=empty or white space) if so, discard.
            if (xmlDataHash != 10) {
                xmlMap.put(key, new String(ch).substring(start, start + length));
            }
        }

        public Map getHashMap() {
            return xmlMap;
        }
    }

    // Jochem Van denbussche
    private class CustomHandler2 extends DefaultHandler {

        /*
         * TODO:
         * - add support for embedded devices
         * - add all elements and attributes
         */
        private CharArrayWriter buffer;
        private Device rootDev;
        private Service currentServ;

        public CustomHandler2() {
            this.buffer = new CharArrayWriter();
            this.rootDev = new Device();
        }

        @Override
        public void startElement(
                String uri, String localName,
                String qName, Attributes attributes)
                throws SAXException {

            buffer.reset();

            if (localName.equalsIgnoreCase("service")) {
                currentServ = new Service(null, null);
                rootDev.addService(currentServ);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {

            if (localName.equalsIgnoreCase("deviceType")) {
                rootDev.setDeviceType(buffer.toString());
            } else if (localName.equalsIgnoreCase("friendlyName")) {
                rootDev.setFriendlyName(buffer.toString());
            } else if (localName.equalsIgnoreCase("serviceType")) {
//                currentServ.setServiceType(buffer.toString());
            } else if (localName.equalsIgnoreCase("serviceId")) {
//                currentServ.setServiceId(buffer.toString());
            }
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {

            buffer.write(ch, start, length);
        }

        public Device getRootDevice() {
            return rootDev;
        }
    }
}
