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
 * ActionImpl.java
 * Created on Jul 11, 2011, 11:03:59 AM
 */
package com.googlecode.mipnp.upnp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ActionImpl implements Action {

    private String name;
    private List<Parameter> parameters;

    public ActionImpl(String name) {
        this.name = name;
        this.parameters = new ArrayList<Parameter>();
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public boolean addParameter(Parameter parameter) {
        return parameters.add(parameter);
    }

    public boolean removeParameter(Parameter parameter) {
        return parameters.remove(parameter);
    }

    public Parameter getParameter(String name) {
        for (Parameter param : parameters) {
            if (param.getName().equals(name)) {
                return param;
            }
        }
        return null;
    }
}
