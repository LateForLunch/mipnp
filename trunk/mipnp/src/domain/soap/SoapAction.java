/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Tijl Van Assche
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
package domain.soap;

import domain.http.HttpRequest;
import java.net.Socket;

/**
 *
 * @author Tijl Van Assche
 */
public class SoapAction extends HttpRequest {

    public SoapAction() {
        super();
    }

    public SoapAction(Socket socket) {
        super(socket);
    }
}
