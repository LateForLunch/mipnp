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
 * CdsObject.java
 * Created on Jul 27, 2011, 1:48:33 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class CdsObject implements CdsConstants, Iterable<CdsObject> {

    private static int nextId = 1000;

    private boolean container;
    private List<CdsObject> children;
    private Resource resource;
    private Map<String, String> properties;

    public CdsObject(String upnpClass) {
        this(upnpClass, null);
    }

    public CdsObject(String upnpClass, String id) {
        this(upnpClass, id, null);
    }

    public CdsObject(String upnpClass, String id, String title) {
        if (!upnpClass.startsWith(UPNP_CLASS_ITEM) &&
                !upnpClass.startsWith(UPNP_CLASS_CONTAINER)) {

            throw new IllegalArgumentException(
                    "UPnP Class must start with \"" +
                    UPNP_CLASS_ITEM + "\" or \"" +
                    UPNP_CLASS_CONTAINER + "\".");
        }

        this.properties = new HashMap<String, String>();
        properties.put(PROPERTY_CLASS, upnpClass);
        if (id == null) {
            properties.put(PROPERTY_ID, String.valueOf(nextId));
            nextId++;
        } else {
            properties.put(PROPERTY_ID, id);
        }
        properties.put(PROPERTY_TITLE, title);
        if (upnpClass.startsWith(UPNP_CLASS_ITEM)) {
            this.container = false;
        } else {
            this.container = true;
            this.children = new ArrayList<CdsObject>();
        }
    }

    public String getUpnpClass() {
        return getProperty(PROPERTY_CLASS);
    }

    public String getId() {
        return getProperty(PROPERTY_ID);
    }

    public String getParentId() {
        String parentId = getProperty(PROPERTY_PARENT_ID);
        if (parentId == null) {
            return "-1";
        }
        return parentId;
    }

    protected void setParentId(String parentId) {
        setProperty(PROPERTY_PARENT_ID, parentId);
    }

    public String getTitle() {
        return getProperty(PROPERTY_TITLE);
    }

    public void setTitle(String title) {
        setProperty(PROPERTY_TITLE, title);
    }

    public boolean isContainer() {
        return container;
    }

    public boolean isItem() {
        return !isContainer();
    }

    public String getProperty(String property) {
        return properties.get(property);
    }

    public void setProperty(String property, String value) {
        if (value != null) {
            properties.put(property, value);
        }
    }

    public boolean containsProperty(String property) {
        return properties.containsKey(property);
    }

    public Set<String> getProperties() {
        return properties.keySet();
    }

    public void addChild(CdsObject child) {
        addChild(child, true);
    }

    public void addChild(CdsObject child, boolean setParent) {
        if (isContainer() && child != null) {
            if (setParent) {
                child.setParentId(getId());
            }
            for (CdsObject ch : getChildren()) {
                if (ch.equals(child)) {
                    return;
                }
            }
            children.add(child);
        }
    }

    public int getNumberOfChildren() {
        if (isContainer()) {
            return getChildren().size();
        } else {
            return 0;
        }
    }

    public List<CdsObject> getChildren() {
        return children;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Iterator<CdsObject> iterator() {
        return new CdsObjectIterator(this);
    }
}
