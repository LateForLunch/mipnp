/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author nicholaihel
 */
public class XMLManager {

   public static void main (String[] args){
       //testxml file:
       //testxml schema:
            TestXML test = new TestXML(new File("/Users/nicholaihel/NetBeansProjects/trunk/mipnp/src/resources/mipnpXML_1.xml"));
            test.run();

   }

   static class TestXML{

     private SAXParserFactory saxParserFactory;
       private File xmlDoc;

       public TestXML(File xmlDoc){
        saxParserFactory = SAXParserFactory.newInstance();
        this.xmlDoc = xmlDoc;
       }

       public void run(){
            try {

                //SAX
                saxParserFactory.setValidating(true);
                saxParserFactory.setNamespaceAware(false);

                SAXParser parser = saxParserFactory.newSAXParser();
                SaxDeviceHandler handler = new SaxDeviceHandler();
                  parser.parse(xmlDoc, handler );
                  
                  System.out.println(handler.m.size()+"\n");

                  System.out.println("VALUES:");
                  Iterator i = handler.m.values().iterator();
                  while(i.hasNext()){
                     System.out.println(i.next());
                  }

                  System.out.println("KEYS: ");
                  Iterator keyIterator = handler.m.keySet().iterator();
                  while (keyIterator.hasNext()){
                  System.out.println(keyIterator.next());
                  }


//                //DOM
//                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
//                domFactory.setValidating(false);
//                domFactory.setNamespaceAware(false); //geen idee wa dit is
//
//                DocumentBuilder builder = domFactory.newDocumentBuilder();
//                Document doc = builder.parse(xmlDoc);
//
//                System.out.println("Root element " + doc.getDocumentElement().getNodeName());
//
//                System.out.println("kinderen root: "+doc.getDocumentElement().getChildNodes().getLength());
//                NodeList l = doc.getDocumentElement().getChildNodes();
//
//                for(int i = 0;i<l.getLength();i++){
//                    System.out.println("blabla==="+l.item(i).getNodeName());
//                }
//                System.out.println("blabla==="+doc.getElementsByTagName("friendlyName").item(0).getNodeValue());
               


                
            } catch (IOException ex) {
                Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            }

       }

       static class SaxDeviceHandler extends DefaultHandler{

           private Device device;
           private static String key;
           public HashMap m;
           int teller = 0;

           public SaxDeviceHandler(){device = new Device(); m = new HashMap<String, Object>();}

        public void startDocument() throws SAXException {
            System.out.println("XML parsing started");
        }


        public void endDocument() throws SAXException {
            System.out.println("XML parsing finished");
        }

        
        public void startElement(String uri, String localName,
                String qName, Attributes attrs) throws SAXException {
            System.out.println("start van element: "+qName);
            

            if (qName.toLowerCase().equals("service")){
                    teller++;
                    System.out.println("service gezien: "+teller);
            }

            if (qName.equals("serviceType") || qName.equals("serviceId") ||
                    qName.equals("SCPDURL") || qName.equals("controlURL") ||
                    qName.equals("eventSubURL")) {
               String temp = String.copyValueOf(qName.toCharArray());
            StringBuilder stb = new StringBuilder(temp).append(teller);
            key = stb.toString();
            }else{key=qName;}
//            } else if (qName.toLowerCase().equals("devicetype")) {
//            } /* if (...)
//                        } */ else {
//                throw new IllegalArgumentException("Element '" +
//                        qName + "' is not allowed here");
//                        }
            
        }
           

        // we leave element 'qName' without any actions:
        public void endElement(String uri, String localName, String qName)
        throws SAXException {
           //device.setUrlBase(ch.toString());
           System.out.println("einde van element: "+qName);
        }

       public void characters(char[] ch, int start, int length) {
           System.out.println("-----------------\nCHARACTER DATA");
           System.out.println("START: "+start+"\nLENGTH: "+length);
           int hash = new String(ch).substring(start, start+length).hashCode();
           System.out.println("VALUE: "+new String(ch).substring(start, start+length).hashCode());
           System.out.println("-----------------");

           if(hash != 10)
               m.put(key, new String(ch).substring(start, start+length));
       }

       }

   }
}





