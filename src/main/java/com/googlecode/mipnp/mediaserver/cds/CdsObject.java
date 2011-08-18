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
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class CdsObject implements CdsConstants {

    private static int nextId = 1000;

    private String upnpClass;
    private String id;
    private CdsObject parent;
    private String title;
    private boolean container;
    private List<CdsObject> children;
    private Resource resource;

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

        this.upnpClass = upnpClass;
        if (id == null) {
            this.id = String.valueOf(nextId);
            nextId++;
        } else {
            this.id = id;
        }
        this.title = title;
        if (upnpClass.startsWith(UPNP_CLASS_ITEM)) {
            this.container = false;
        } else {
            this.container = true;
            this.children = new ArrayList<CdsObject>();
        }
    }

    public String getUpnpClass() {
        return upnpClass;
    }

    public String getId() {
        return id;
    }

//    protected void setId(String id) {
//        this.id = id;
//    }

    public CdsObject getParent() {
        return parent;
    }

    protected void setParent(CdsObject parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isContainer() {
        return container;
    }

    public boolean isItem() {
        return !isContainer();
    }

    public void addChild(CdsObject child) {
        if (isContainer() && child != null) {
            child.setParent(this);
            children.add(child);
        }
    }

    public CdsObject getObjectById(String id) {
        if (isContainer()) {
            for (CdsObject child : getChildren()) {
                if (child.getId().equals(id)) {
                    return child;
                }
            }
            CdsObject result = null;
            for (CdsObject child : getChildren()) {
                result = child.getObjectById(id);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
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

    public boolean contains(String upnpClass) {
        if (isItem()) {
            return false;
        }
        for (CdsObject child : getChildren()) {
            if (child.isContainer()) {
                if (child.contains(upnpClass)) {
                    return true;
                }
            } else if (child.getUpnpClass().startsWith(upnpClass)) {
                return true;
            }
        }
        return false;
    }
}
