/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jeroen De Wilde, Jochem Van denbussche
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
import java.util.List;

/**
 * 
 * @author Jeroen De Wilde, Jochem Van denbussche
 */
public class Service implements IService {

    private String type;
    private String id;
    private URL descriptionURL;
    private URL controlURL;
    private URL eventSubURL;

    private List<Action> actions;
    private List<StateVariable> serviceStateTable;

    public Service(String type, String id){
        this.type = type;
        this.id = id;
        //actions = new ArrayList<Action>();
        //serviceStateTable = new ArrayList<StateVariable>();
    }

    public Service() {}

    /**
     * @return the description URL
     */
    public URL getDescriptionURL() {
        return descriptionURL;
    }

    /**
     * @param descriptionURL the description URL to set
     */
    public void setDescriptionURL(URL descriptionURL) {
        this.descriptionURL = descriptionURL;
    }

    /**
     * @return the control URL
     */
    public URL getControlURL() {
        return controlURL;
    }

    /**
     * @param controlURL the control URL to set
     */
    public void setControlURL(URL controlURL) {
        this.controlURL = controlURL;
    }

    /**
     * @return the URL for eventing
     */
    public URL getEventSubURL() {
        return eventSubURL;
    }

    /**
     * @param eventSubURL the URL for eventing to set
     */
    public void setEventSubURL(URL eventSubURL) {
        this.eventSubURL = eventSubURL;
    }

    /**
     * @return the service type
     */
    public String getServiceType() {
        return type;
    }

    /**
     * @param type the service type to set
     */
    public void setServiceType(String type) {
        this.type = type;
    }

    /**
     * @return the id
     */
    public String getServiceId() {
        return id;
    }

    /**
     * @param id the service id to set
     */
    public void setServiceId(String id) {
        this.id = id;
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
