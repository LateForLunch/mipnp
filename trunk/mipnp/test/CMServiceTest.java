/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.upnp.Service;
import org.junit.Assert;
import domain.upnp.services.ServiceFactory;
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
    private static Service cm;

    public CMServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        cm = new Service();
        ServiceFactory.initializeService(cm,"connectionmanager");
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