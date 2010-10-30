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
 * HttpServer.java
 * Created on Oct 30, 2010, 9:45:12 PM
 */
package testTools;

import domain.HttpConstants;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpServer implements Runnable, HttpConstants {

    private static final int PORT = 8080;
    private static final String SAMPLE_DATA =
            "<html><body>Java HttpServer works!</body></html>";

    private ServerSocket serverSocket;

    public HttpServer() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run() {
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();

                Scanner input = new Scanner(socket.getInputStream());
                Formatter output = new Formatter(socket.getOutputStream());

                String firstLine = input.nextLine();
                if (!firstLine.startsWith("GET")) {
                    send501(output);
                } else {
                    send200(output);
                    output.format("Date: ", new Date());
                    output.format("Content-Type: text/html");
                    output.format("Content-Length: %s",
                            SAMPLE_DATA.getBytes().length+CRLF+CRLF);
                    output.format(SAMPLE_DATA);
                    output.flush();
                }
            } catch (SocketException ex) { // ServerSocket closed
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void send501(Formatter output) {
        output.format("%s %s %s %s",
                HTTP_VERSION, "501", HTTP_STATUS.get(501), CRLF);
        output.flush();
    }

    private void send200(Formatter output) {
        output.format("%s %s %s %s",
                HTTP_VERSION, "200", HTTP_STATUS.get(200), CRLF);
        output.flush();
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press 'q' to stop.\nCreating HTTP server...");
        HttpServer httpServer = null;
        try {
            httpServer = new HttpServer();
        } catch (IOException ex) {
            System.out.println(" FAILED\n");
            ex.printStackTrace();
            System.exit(1);
        }
        Thread thread = new Thread(httpServer);
        System.out.println(" OK\n");
        thread.start();
        while (!(scanner.nextLine().equalsIgnoreCase("q"))) {
            System.out.println("Unknown command.\nPress 'q' to stop.\n");
        }
        httpServer.stop();
    }
}
