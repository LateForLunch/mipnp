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
 * UPnPParam.java
 * Created on Jul 7, 2011, 12:58:36 PM
 */
package com.googlecode.mipnp.upnp;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public @interface UPnPParam {

    public enum Mode {
        IN,
        OUT;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    String name();

    Mode direction();
}
