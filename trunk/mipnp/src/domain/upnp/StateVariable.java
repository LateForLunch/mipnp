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

package domain.upnp;

import java.util.List;

/**
 *
 * @author Jeroen De Wilde
 */
public class StateVariable {
    private String name;
    private String dataType;
    private String defaultValue;
 // private List<AllowedValue> allowedValueList;
    private List<String> allowedValueList;
    private String value;
    private boolean sendEvents = false;

    public StateVariable(String name, String dataType, boolean sendEvents){
        this.name = name;
        this.dataType = dataType;
        this.sendEvents = sendEvents;
    }

    public StateVariable(String name, String dataType, String defaultValue, boolean sendEvents){
        this.name = name;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.sendEvents = sendEvents;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the sendEvents
     */
    public boolean isSendEvents() {
        return sendEvents;
    }

    /**
     * @param sendEvents the sendEvents to set
     */
    public void setSendEvents(boolean sendEvents) {
        this.sendEvents = sendEvents;
    }

    /**
     * @return the allowedValueList
     */
    public List<String> getAllowedValueList() {
        return allowedValueList;
    }

    /**
     * @param allowedValueList the allowedValueList to set
     */
    public void setAllowedValueList(List<String> allowedValueList) {
        this.allowedValueList = allowedValueList;
    }

}
