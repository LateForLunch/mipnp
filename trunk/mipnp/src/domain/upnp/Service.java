/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jeroen De Wilde
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
 * Service.java
 * Created on Oct 23, 2010, 3:37:50 PM
 */
package domain.upnp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private String serviceType;
    private String serviceId;

    //all URLS:
    private URL scpdURL;
    private URL controlURL;
    private URL eventSubURL;

    private List<Action> actions;
    private List<StateVariable> serviceStateTable;

    public Service(){}
    
    public Service(String serviceId, String serviceType){
        this.serviceId=serviceId;
        this.serviceType=serviceType;
        //actions = new ArrayList<Action>();
        //serviceStateTable = new ArrayList<StateVariable>();
    }

    /**
     * @return the scpdURL
     */
    public URL getScpdURL() {
        return scpdURL;
    }

    /**
     * @param scpdURL the scpdURL to set
     */
    public void setScpdURL(URL scpdURL) {
        this.scpdURL = scpdURL;
    }

    /**
     * @return the controlURL
     */
    public URL getControlURL() {
        return controlURL;
    }

    /**
     * @param controlURL the controlURL to set
     */
    public void setControlURL(URL controlURL) {
        this.controlURL = controlURL;
    }

    /**
     * @return the eventSubURL
     */
    public URL getEventSubURL() {
        return eventSubURL;
    }

    /**
     * @param eventSubURL the eventSubURL to set
     */
    public void setEventSubURL(URL eventSubURL) {
        this.eventSubURL = eventSubURL;
    }

    /**
     * @return the serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return the serviceId
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the actions
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * @param actions the actions to set
     */
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    /**
     * @return the serviceStateTable
     */
    public List<StateVariable> getServiceStateTable() {
        return serviceStateTable;
    }

    /**
     * @param serviceStateTable the serviceStateTable to set
     */
    public void setServiceStateTable(List<StateVariable> serviceStateTable) {
        this.serviceStateTable = serviceStateTable;
    }
}
