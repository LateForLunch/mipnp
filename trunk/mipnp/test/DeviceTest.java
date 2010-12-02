/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.Service;
import junit.framework.Assert;
import domain.ConfigurationManager;
import domain.Device;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nicholaihel
 */
public class DeviceTest {
    private static Device d;

    public DeviceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
         ConfigurationManager cm = new ConfigurationManager();
         d = cm.createDeviceFromXML("src/resources/mipnpXML_1.xml");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void TestDeviceNotNull() {
     Assert.assertNotNull(d);
     }

     @Test
     public void TestDeviceOK() {
     // UUID
     Assert.assertEquals("uuid:UUID", d.getUdn());
     //<friendlyName>MiPnP v0.1</friendlyName>
     Assert.assertEquals("MiPnP v0.1", d.getFriendlyName());
     //<deviceType>urn:schemas-upnp-org:device:MediaServer:1</deviceType>
     Assert.assertEquals("urn:schemas-upnp-org:device:MediaServer:1", d.getDeviceType());
     //<manufacturer>MiPnP devs</manufacturer>
     Assert.assertEquals("MiPnP devs",d.getManufacturer());
     //<manufacturerURL>https://code.google.com/p/mipnp/</manufacturerURL>
     Assert.assertEquals("MiPnP: Minimal UPnP MediaServer",d.getModelDescription());
        //<modelDescription>MiPnP: Minimal UPnP MediaServer</modelDescription>
        //<modelName>Modelname: MiPnP xxx</modelName>
        //<modelNumber>01</modelNumber>
        //<modelURL>https://code.google.com/p/mipnp/</modelURL>
        //<serialNumber>serialNumber</serialNumber>
        //<UDN>uuid:UUID</UDN>
        //<UPC>Universal Product Code</UPC>
     }

     @Test
     public void TestDeviceServices() {
         /*
          * <serviceList>
            <service>
            <serviceType>urn:schemas-upnp-org:service:ContentDirectory:1 </serviceType>
            <serviceId>urn:upnp-org:serviceId:ContentDirectory </serviceId>
            <SCPDURL>http://www.CDdescription.com</SCPDURL>
            <controlURL>http://www.CDcontrol.com</controlURL>
            <eventSubURL>http://www.CDeventing.com</eventSubURL>
            </service>
            <service>
            <serviceType>urn:schemas-upnp-org:service:ConnectionManager:1</serviceType>
            <serviceId>urn:upnp-org:serviceId:ConnectionManager</serviceId>
            <SCPDURL>http://www.CMdescription.com</SCPDURL>
            <controlURL>http://www.CMcontrol.com</controlURL>
            <eventSubURL>http://www.CMeventing.com</eventSubURL>
            </service>
            </serviceList>
          */
         //Test of twee services (CD en CM)
         Assert.assertTrue(d.getServices().size() == 2);

         //Test Contentdirectory
         Service contentDirectory = d.getServices().get(0);
         Assert.assertEquals("urn:schemas-upnp-org:service:ContentDirectory:1", contentDirectory.getServiceType());
         Assert.assertEquals("urn:upnp-org:serviceId:ContentDirectory", contentDirectory.getServiceId());
         Assert.assertEquals("http://www.CDdescription.com", contentDirectory.getScpdURL().toString());

         //Test ConnectionManager
         Service connectionManager = d.getServices().get(1);
     }

}