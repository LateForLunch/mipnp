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
 * StateVariable.java
 * Created on Jul 9, 2011, 1:38:57 PM
 */
package com.googlecode.mipnp.upnp;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class StateVariable<T> {

    public enum DataType {
        UI1,
        UI2,
        UI4,
        I1,
        I2,
        I4,
        INT,
        R4,
        R8,
        NUMBER,
        FIXED_14_4,
        FLOAT,
        CHAR,
        STRING,
        DATE,
        DATE_TIME,
        DATE_TIME_TZ,
        TIME,
        TIME_TZ,
        BOOLEAN,
        BIN_BASE64,
        BIN_HEX,
        URI,
        UUID;

        @Override
        public String toString() {
            switch (this) {
                case DATE_TIME:
                    return "dateTime";
                case DATE_TIME_TZ:
                    return "dateTime.tz";
                default:
                    return super.toString().toLowerCase().replaceAll("_", ".");
            }
        }
    }

//    TODO: private static final EventSender...

    private String name;
    private DataType dataType;
    private T value;
    private boolean sendEvents;
    private boolean multicast;

    public StateVariable(String name, DataType dataType, T value) {
        this(name, dataType, value, true, false);
    }

    public StateVariable(String name, DataType dataType, T value,
            boolean sendEvents, boolean multicast) {

        this.name = name;
        this.dataType = dataType;
        this.value = value;
        this.sendEvents = sendEvents;
        this.multicast = multicast;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        if (getSendEvents() || getMulticast()) {
            // TODO: eventSender.unicast...
        }
        if (getMulticast()) {
            // TODO: eventSender.multicast...
        }
    }

    public boolean getSendEvents() {
        return sendEvents;
    }

    public void setSendEvents(boolean sendEvents) {
        this.sendEvents = sendEvents;
    }

    public boolean getMulticast() {
        return multicast;
    }

    public void setMulticast(boolean multicast) {
        this.multicast = multicast;
    }
}
