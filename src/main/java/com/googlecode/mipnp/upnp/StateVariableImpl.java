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
 * StateVariableImpl.java
 * Created on Jul 11, 2011, 11:26:47 AM
 */
package com.googlecode.mipnp.upnp;

import com.googlecode.mipnp.upnp.StateVariable.DataType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class StateVariableImpl implements StateVariable {

    private String name;
    private DataType dataType;
//    private T value;
    private String value;
//    private T defaultValue;
    private String defaultValue;
    private List<String> allowedValues;
    private boolean sendEvents;
    private boolean multicast;

    public StateVariableImpl(String name, DataType dataType) {
        this(name, dataType, null, true, false);
    }

    public StateVariableImpl(
            String name,
            DataType dataType,
            boolean sendEvents,
            boolean multicast) {

        this(name, dataType, null, sendEvents, multicast);
    }

    public StateVariableImpl(
            String name,
            DataType dataType,
            String defaultValue,
            boolean sendEvents,
            boolean multicast) {

        this.name = name;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.allowedValues = new ArrayList<String>();
        this.sendEvents = sendEvents;
        this.multicast = multicast;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        // TODO: send events
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public List<String> getAllowedValues() {
        return allowedValues;
    }

    public boolean addAllowedValue(String value) {
        return allowedValues.add(value);
    }

    public boolean removeAllowedValue(String value) {
        return allowedValues.remove(value);
    }

    public boolean hasToSendEvents() {
        return sendEvents;
    }

    public boolean hasToMulticast() {
        return multicast;
    }
}
