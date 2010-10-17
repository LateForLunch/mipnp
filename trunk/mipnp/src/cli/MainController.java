/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainController.java
 * Created on Oct 17, 2010, 3:53:30 PM
 */
package cli;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MainController {

    private MainCli cli;
    private ExitListener exitListener;

    public MainController() {
        this.cli = new MainCli();
        this.exitListener = new ExitListener();
        new Thread(exitListener).start();
    }
}
