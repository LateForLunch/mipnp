/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010, 2011  Jochem Van denbussche
 *
 * This file is part of MiPnP.
 *
 * MiPnP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MiPnP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Parameter.java
 * Created on Jul 10, 2011, 11:19:51 AM
 */
package com.googlecode.mipnp.upnp;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public interface Parameter {

    public enum Mode {
        IN,
        OUT;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    /**
     * Returns the name of the parameter.
     * @return the name of the parameter
     */
    String getName();

    /**
     * Returns the direction of the parameter.
     * @return the direction of the parameter
     */
    Mode getDirection();

    /**
     * Returns the related state variable of the parameter.<br/>
     * @return the related state variable of the parameter
     */
    StateVariable getRelatedStateVariable();
}
