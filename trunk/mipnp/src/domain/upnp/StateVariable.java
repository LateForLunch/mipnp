/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.upnp;

import java.util.List;

/**
 *
 * @author nicholaihel
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
