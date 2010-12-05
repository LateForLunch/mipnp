/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.xml;

import domain.upnp.Service;
import domain.upnp.services.ConnectionManager;
import domain.upnp.services.ContentDirectory;
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
 * @author nicholaihel
 */
class ServiceHandler extends DefaultHandler {

        private CharArrayWriter buffer;
        private XMLReader xmlReader;
        private ContentHandler parent;
        private Service currentServ;

        public ServiceHandler() {
            this.buffer = new CharArrayWriter();
        }

        public void handleService(
                Service newServ, XMLReader xmlReader, ContentHandler parent) {

            this.currentServ = newServ;
            this.xmlReader = xmlReader;
            this.parent = parent;
            xmlReader.setContentHandler(this);

        }

        @Override
        public void startElement(
                String uri, String localName,
                String qName, Attributes attributes)
                throws SAXException {

            buffer.reset();
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            try {

            if (qName.equalsIgnoreCase("serviceType")) {
                currentServ.setServiceType(buffer.toString());
            } else if (qName.equalsIgnoreCase("serviceId")) {
                //test door jeroen
                String currentServiceId = buffer.toString();
                if(currentServiceId.equalsIgnoreCase("urn:upnp-org:serviceId:ContentDirectory")){                   
                    System.out.println("CD ontdekt");
                    ContentDirectory.initializeContentDirectory(currentServ);
                } else if(currentServiceId.equalsIgnoreCase("urn:upnp-org:serviceId:ConnectionManager")){
                   //
                } else {
                    throw new IllegalArgumentException("not implemented!");
                }
                currentServ.setServiceId(currentServiceId);
            } else if (qName.equalsIgnoreCase("SCPDURL")) {
                    currentServ.setScpdURL(new URL(buffer.toString()));
            } else if (qName.equalsIgnoreCase("controlURL")) {
                    currentServ.setControlURL(new URL(buffer.toString()));
            } else if (qName.equalsIgnoreCase("eventSubURL")) {
                    currentServ.setEventSubURL(new URL(buffer.toString()));
            } else if (qName.equalsIgnoreCase("service")) {
                xmlReader.setContentHandler(parent);
            }

            } catch (MalformedURLException ex) {
                   System.out.println("malformed URL");
                   ex.printStackTrace();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {

            buffer.write(ch, start, length);
        }
    }
