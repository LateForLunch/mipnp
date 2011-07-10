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
 * Created on Jul 10, 2011, 11:22:13 AM
 */
package com.googlecode.mipnp.upnp;

import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public interface StateVariable<T> {

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

    /**
     * Returns the name of the state variable.
     * @return the name of the state variable
     */
    String getName();

    /**
     * Returns the data type of the state variable.
     * @return the data type of the state variable
     */
    DataType getDataType();

    /**
     * Returns the value of the state variable.
     * @return the value of the state variable
     */
    T getValue();

    /**
     * Returns the default value of the state variable or 
     * null if the state variable does not have a default value.
     * @return the default value or null
     */
    T getDefaultValue();

    /**
     * Returns a list of allowed values or null if the state variable 
     * does not have restrictions on allowed values.<br/>
     * Allowed values may only be used with the string type.
     * @return a list of allowed values or null
     */
    List<String> getAllowedValues();

    // TODO: allowedValueRange

    /**
     * Returns true if event messages must be generated when 
     * the value of this state variable changes.
     * @return true if event messages must be generated when 
     * the value of this state variable changes
     */
    boolean hasToSendEvents();

    /**
     * Returns true if event messages must be delivered using multicast eventing.
     * @return true if event messages must be delivered using multicast eventing
     */
    boolean hasToMulticast();
}
