/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.upnp.services.ServiceFactory;
import domain.upnp.Service;
import org.junit.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jeroen De Wilde
 */
public class CDServiceTest {

    private static Service cd;

    public CDServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        cd = new Service();
        ServiceFactory.initializeService(cd,"contentdirectory");
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
     public void testActionListSize() {
        Assert.assertTrue(cd.getActions().size() == 6);
     }

     @Test
     public void testServiceStateTableSize() {
        Assert.assertTrue(cd.getServiceStateTable().size() == 12);
     }

}