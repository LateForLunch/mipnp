/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Assert;
import domain.upnp.services.ContentDirectory;
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
public class CDServiceTest {

    private static ContentDirectory cd ;

    public CDServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
         cd = new ContentDirectory();
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