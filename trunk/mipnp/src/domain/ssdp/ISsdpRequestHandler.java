/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche, Tijl Van Assche
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
 * ISsdpRequestHandler.java
 * Created on Dec 4, 2010, 4:44:57 PM
 */
package domain.ssdp;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public interface ISsdpRequestHandler {

    void handleSsdpRequest(SsdpRequest request);
}
