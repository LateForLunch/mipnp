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
 * ParameterImpl.java
 * Created on Jul 11, 2011, 11:08:30 AM
 */
package com.googlecode.mipnp.upnp;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ParameterImpl implements Parameter {

    private String name;
    private Mode direction;
    private StateVariable relatedStateVariable;

    public ParameterImpl(
            String name, Mode direction, StateVariable relatedStateVariable) {

        this.name = name;
        this.direction = direction;
        this.relatedStateVariable = relatedStateVariable;
    }

    public String getName() {
        return name;
    }

    public Mode getDirection() {
        return direction;
    }

    public StateVariable getRelatedStateVariable() {
        return relatedStateVariable;
    }
}
