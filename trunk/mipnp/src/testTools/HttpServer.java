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

import domain.http.HttpConstants;
import domain.Packet;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpServer implements Runnable, HttpConstants {

    private static final int PORT = 8080;
    private static final String SAMPLE_DATA =
            "<html><body>Java HttpServer works!µ</body></html>";
    private static final Charset UTF8 = Charset.forName("utf-8");
    private static final Charset ASCII = Charset.forName("US-ASCII");

    private ServerSocket serverSocket;

    public HttpServer() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run() {
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();

                Scanner input = new Scanner(socket.getInputStream(), "US-ASCII");
//                Formatter output = new Formatter(socket.getOutputStream(), "US-ASCII");
                Packet packet = new Packet();

                String firstLine = input.nextLine();
                if (!firstLine.startsWith("GET")) {
                    send501(packet);
                } else {
                    send200(packet);
//                    output.format("Date: ", new Date());
//                    output.format("Content-Type: text/html");
//                    output.format("Content-Length: %s",
//                            SAMPLE_DATA.getBytes().length+CRLF+CRLF);
//                    output.format(SAMPLE_DATA);
//                    output.flush();
                    packet.write("Date: " + new Date(), ASCII);
                    packet.write("Content-Type: text/html; charset=utf-8", ASCII);
                    ByteBuffer bb = UTF8.encode(SAMPLE_DATA);
                    packet.write("Content-Length: " + bb.array().length + CRLF + CRLF, ASCII);
                    packet.write(bb.array());
                }
                packet.flush();
                packet.writeTo(socket.getOutputStream());
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

    private void send501(Packet packet) throws IOException {
//        output.format("%s %s %s %s",
//                HTTP_VERSION, "501", HTTP_STATUS.get(501), CRLF);
//        output.flush();
        packet.write(HTTP_VERSION + " 501 " + HTTP_STATUS.get(501) + CRLF);
    }

    private void send200(Packet packet) throws IOException {
//        output.format("%s %s %s %s",
//                HTTP_VERSION, "200", HTTP_STATUS.get(200), CRLF);
//        output.flush();
        packet.write(HTTP_VERSION + " 200 " + HTTP_STATUS.get(200) + CRLF, ASCII);
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
