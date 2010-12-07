/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.xml;

import domain.upnp.Device;
import java.io.CharArrayWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class CustomHandler2 extends DefaultHandler {

        private XMLReader xmlReader;
        private CharArrayWriter buffer;
        private DeviceHandler deviceHandler;
        private ServiceHandler serviceHandler;
        private Device rootDev;
        private Device currentDev;
        private boolean inDevList;

        public CustomHandler2(XMLReader xmlReader) {
            this.xmlReader = xmlReader;
            this.buffer = new CharArrayWriter();
            this.deviceHandler = new DeviceHandler();
            this.rootDev = new Device();
            this.currentDev = rootDev;
            this.inDevList = false;
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

            if (qName.equalsIgnoreCase("device")) {
                Device dev = rootDev;
                if (inDevList) {
                    dev = new Device();
//                    rootDev.addEmbeddedDevice(dev); // TODO (low priority) add support for embedded devices
                    currentDev = dev;
                }
                deviceHandler.handleDevice(dev, xmlReader, this);
                } else if (qName.equalsIgnoreCase("deviceList")) {
                    this.inDevList = true;
            }
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

            if (localName.equalsIgnoreCase("deviceList")) {
                this.inDevList = false;
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

        public Device getRootDevice() {
            return rootDev;
        }
    }
