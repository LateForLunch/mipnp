/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Assert;
import domain.upnp.services.ConnectionManager;
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
public class CMServiceTest {
    private static ConnectionManager cm;

    public CMServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        cm = new ConnectionManager();
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