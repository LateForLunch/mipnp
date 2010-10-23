/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jeroen De Wilde, Jochem Van denbussche
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
 * Device.java
 * Created on Oct 23, 2010, 3:36:01 PM
 */
package domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeroen De Wilde, Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Device {

    private List<Device> embeddedDevices;
    private List<Service> services;

    public Device() {
        this.embeddedDevices = new ArrayList<Device>();
        this.services = new ArrayList<Service>();
    }
}
