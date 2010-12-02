/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche
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
 * MainController.java
 * Created on Oct 17, 2010, 3:53:30 PM
 */
package cli;

import cli.menu.CliMenu;
import domain.tools.NetworkInterfaceTools;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MainController {

    private MainCli cli;
    private ExitListener exitListener;

    public MainController(String[] args) {
        Settings.parseArguments(args);
        Settings.checkSettings();
        this.cli = new MainCli();
        this.exitListener = new ExitListener(this);
        new Thread(exitListener).start();
    }

    public void exit(int status) {
        System.exit(status);
    }
}
