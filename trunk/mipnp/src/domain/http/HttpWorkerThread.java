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
 * HttpWorkerThread.java
 * Created on Dec 2, 2010, 1:12:15 PM
 */
package domain.http;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class HttpWorkerThread implements Runnable {

    private HttpServer server;
    private Socket socket;

    public HttpWorkerThread(HttpServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void run() {
        try {
            HttpRequest request = new HttpRequest();
            request.setInputStream(socket.getInputStream());
            request.setOutputStream(socket.getOutputStream());
            request.parse();
            server.notifyHandlers(request);
        } catch (Exception ex) {
            ex.printStackTrace(); // TODO
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                // ignore
            }
        }
    }
}
