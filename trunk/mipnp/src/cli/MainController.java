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

import domain.http.HttpServer;
import domain.shutdown.ShutdownHook;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MainController {

    private ShutdownHook shutdownHook;
    private ExitListener exitListener;
//    private HttpServer httpServer;
//    private HttpServerController httpServerController;

    public MainController(String[] args) {
        shutdownHook = new ShutdownHook();
        Settings.parseArguments(args);
        Settings.checkSettings();
        init();
    }

    private void init() {
        this.exitListener = new ExitListener(this);
        shutdownHook.addShutdownListener(exitListener);
        new Thread(exitListener).start();
        int httpPort = 0;
        try {
            httpPort = Integer.parseInt(Settings.getProperty(Settings.HTTP_PORT));
        } catch (NumberFormatException ex) {
            System.err.println(
                    "Illegal HTTP port: " + Settings.getProperty(Settings.HTTP_PORT));
            exit(1);
        }
        InetAddress httpAddress = null;
        try {
            httpAddress = InetAddress.getByName(Settings.getProperty(Settings.BIND_ADDRESS));
        } catch (UnknownHostException ex) {
            System.err.println(
                    "Illegal bind address: " + Settings.getProperty(Settings.BIND_ADDRESS));
            exit(1);
        }
//        this.httpServer = new HttpServer(httpPort, 0, httpAddress);
//        this.httpServerController = new HttpServerController(shutdownHook, httpServer);
    }

    private void exit(int status) {
        System.exit(status);
    }
}
