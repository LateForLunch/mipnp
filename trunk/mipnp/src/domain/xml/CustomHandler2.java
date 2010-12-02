/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.xml;

import domain.Device;
import java.io.CharArrayWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author nicholaihel
 */
public class CustomHandler2 extends DefaultHandler {

        /*
         * TODO:
         * - test code
         * - add all elements and attributes
         */
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
           // this.serviceHandler = new ServiceHandler();
            this.rootDev = new Device();
            this.currentDev = rootDev;
            this.inDevList = false;
             //System.out.println("in customhandler -  constructor");
        }

        @Override
        public void startElement(
                String uri, String localName,
                String qName, Attributes attributes)
                throws SAXException {

            buffer.reset();
            //System.out.println("in customhandler - start element: "+qName);

            if (qName.equalsIgnoreCase("device")) {
                Device dev = rootDev;
                if (inDevList) { //????
                    dev = new Device();
                    rootDev.addEmbeddedDevice(dev);
                    currentDev = dev;
                }
                deviceHandler.handleDevice(dev, xmlReader, this);
                } else if (qName.equalsIgnoreCase("deviceList")) {
                    this.inDevList = true;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {

            if (localName.equalsIgnoreCase("deviceList")) {
                this.inDevList = false;
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
