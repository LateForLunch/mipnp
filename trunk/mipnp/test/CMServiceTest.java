/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.upnp.Service;
import org.junit.Assert;
import domain.upnp.services.ServiceFactory;
import domain.xml.ConfigurationManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jeroen De Wilde
 */
public class CMServiceTest {
    private Service cm;

    public CMServiceTest() {
          cm = new ConfigurationManager().createDeviceFromXML("src/resources/mipnpXML_1.xml").getServices().get(1);
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
     public void testServiceStateTableSize() {
     Assert.assertTrue(cm.getServiceStateTable().size() == 10);
     }

      @Test
     public void testActionListSize() {
     Assert.assertTrue(cm.getActions().size() == 3);
     }

}