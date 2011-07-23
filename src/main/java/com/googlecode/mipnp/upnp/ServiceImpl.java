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
 * ServiceImpl.java
 * Created on Jun 30, 2011, 3:54:43 PM
 */
package com.googlecode.mipnp.upnp;

import com.googlecode.mipnp.upnp.xml.ServiceDescriptionParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ServiceImpl implements Service {

    private String vendorDomainName;
    private String type;
    private String id;
//    private String version;
    private int version;
    private URI descriptionUri;
    private URI controlUri;
    private URI eventUri;
    private List<Action> actions;
    private List<StateVariable> stateVariables;

    public ServiceImpl(
            String vendorDomainName, String type, String id, int version) {

        this.vendorDomainName = vendorDomainName;
        this.type = type;
        this.id = id;
        this.version = version;
        this.actions = new ArrayList<Action>();
        this.stateVariables = new ArrayList<StateVariable>();
    }

    public void parseDescription(File descriptionFile)
            throws FileNotFoundException, ParserConfigurationException,
            SAXException, IOException {

        ServiceDescriptionParser parser = new ServiceDescriptionParser(this);
        parser.parse(descriptionFile);
    }

    public void parseDescription(InputStream is)
            throws ParserConfigurationException, SAXException, IOException {

        ServiceDescriptionParser parser = new ServiceDescriptionParser(this);
        parser.parse(is);
    }

    public String getVendorDomainName() {
        return vendorDomainName;
    }

    public void setVendorDomainName(String vendorDomainName) {
        this.vendorDomainName = vendorDomainName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public URI getDescriptionUri() {
        return descriptionUri;
    }

    public void setDescriptionUri(URI descriptionUri) {
        this.descriptionUri = descriptionUri;
    }

    public URI getControlUri() {
        return controlUri;
    }

    public void setControlUri(URI controlUri) {
        this.controlUri = controlUri;
    }

    public URI getEventUri() {
        return eventUri;
    }

    public void setEventUri(URI eventUri) {
        this.eventUri = eventUri;
    }

    public List<Action> getActions() {
        return actions;
    }

    public boolean addAction(Action action) {
        return actions.add(action);
    }

    public boolean removeAction(Action action) {
        return actions.remove(action);
    }

    public Action getAction(String name) {
        for (Action action : actions) {
            if (action.getName().equals(name)) {
                return action;
            }
        }
        return null;
    }

    public List<StateVariable> getStateVariables() {
        return stateVariables;
    }

    public boolean addStateVariable(StateVariable stateVariable) {
        return stateVariables.add(stateVariable);
    }

    public boolean removeStateVariable(StateVariable stateVariable) {
        return stateVariables.remove(stateVariable);
    }

    public StateVariable getStateVariable(String name) {
        for (StateVariable var : stateVariables) {
            if (var.getName().equals(name)) {
                return var;
            }
        }
        return null;
    }
}
