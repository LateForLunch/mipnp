/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.upnp.Service;
import junit.framework.Assert;
import domain.xml.ConfigurationManager;
import domain.upnp.Device;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jeroen De Wilde
 */
public class DeviceTest {
    private  Device d;
    private Service contentDirectory;
    private Service connectionManager;

    public DeviceTest() {
        ConfigurationManager cm = new ConfigurationManager();
         d = cm.createDeviceFromXML("src/resources/mipnpXML_1.xml");
         contentDirectory = d.getServices().get(0);
         connectionManager = d.getServices().get(1);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
         
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
     public void testDeviceNotNull() {
     Assert.assertNotNull(d);
     }

     @Test
     public void testDeviceOK() {
     Assert.assertEquals("uuid:UUID", d.getUdn());
     Assert.assertEquals("MiPnP v0.1", d.getFriendlyName());
     Assert.assertEquals("urn:schemas-upnp-org:device:MediaServer:1", d.getDeviceType());
     Assert.assertEquals("MiPnP devs",d.getManufacturer());
     Assert.assertEquals("MiPnP: Minimal UPnP MediaServer",d.getModelDescription());

     
     }

     @Test
     public void testURLSDevice(){
         //presentation URL device
         Assert.assertEquals("http://192.168.1.3:44444/mipnp/mipnp.html",d.getPresentationURL().toString());
    }

    @Test
    public void testURLSContentdirectory(){
         //presentation URL contentdirectory
         Assert.assertEquals("http://192.168.1.3:44444/mipnp/mipnp_cds.xml",contentDirectory.getScpdURL().toString());
         //control URL contentdirectory
         Assert.assertEquals("http://192.168.1.3:44444/mipnp/mipnp_cds_control",contentDirectory.getControlURL().toString());
         //eventing URL contentdirectory
         Assert.assertEquals("http://192.168.1.3:44444/mipnp/mipnp_cds_event",contentDirectory.getEventSubURL().toString());
    }
    @Test
    public void testURLSConnectionManager(){
         //presentation URL connectionManager
         Assert.assertEquals("http://192.168.1.3:44444/mipnp/mipnp_cms.xml",connectionManager.getScpdURL().toString());
         //control URL connectionManager
         Assert.assertEquals("http://192.168.1.3:44444/mipnp/mipnp_cms_control",connectionManager.getControlURL().toString());
         //eventing URL connectionManager
         Assert.assertEquals("http://192.168.1.3:44444/mipnp/mipnp_cms_event",connectionManager.getEventSubURL().toString());
     }

     @Test
     public void testDeviceServices() {
         //Test of twee services (CD en CM)
         Assert.assertTrue(d.getServices().size() == 2);

         //Test Contentdirectory
         
         Assert.assertEquals("urn:schemas-upnp-org:service:ContentDirectory:1", contentDirectory.getServiceType());
         Assert.assertEquals("urn:upnp-org:serviceId:ContentDirectory", contentDirectory.getServiceId());

         //Test ConnectionManager
         
         Assert.assertEquals("urn:schemas-upnp-org:service:ConnectionManager:1", connectionManager.getServiceType());
         //...
     }

}