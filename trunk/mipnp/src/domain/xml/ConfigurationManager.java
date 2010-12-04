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
package domain.xml;

import domain.upnp.Device;
import domain.upnp.Service;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;

/**
 *
 * @author Jeroen De Wilde
 */
public class ConfigurationManager {

    private SAXParserFactory saxParserFactory;

     public /*static*/ Service addService(String serviceType){

         try{
             //TODO

         }catch(Exception e){ //TODO
            System.out.println("Fout bij addServicePropertiesFromXML");
            e.printStackTrace();;
        }
        return null;
     }

     public /*static*/ Device createDeviceFromXML(String xmlFilePath) {
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
            XMLReader r = parser.getXMLReader();
            CustomHandler2 handler = new CustomHandler2(r);
            //r.setContentHandler(handler);
            //r.setErrorHandler(new MyErrorHandler());
            //r.parse("src/resources/mipnpXML_1.xml");
            parser.parse(xmlDoc, handler);
            return handler.getRootDevice();
            
        }catch(Exception e){
            System.out.println("Fout bij createDeviceFromXML");
            e.printStackTrace();;
        }
        return null;
    }
   
}
