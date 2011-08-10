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
public abstract class CdsObject {

    private static final String CLASS_ITEM = "object.item";
    private static final String CLASS_CONTAINER = "object.container";

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
        this(upnpClass, id, "");
    }

    public CdsObject(String upnpClass, String id, String title) {
        if (!upnpClass.startsWith(CLASS_ITEM) && !upnpClass.startsWith(CLASS_CONTAINER)) {
            throw new IllegalArgumentException(
                    "UPnP Class must start with \"" +
                    CLASS_ITEM + "\" or \"" + CLASS_CONTAINER + "\".");
        }
        if (id == null) {
            this.id = String.valueOf(nextId);
            nextId++;
        } else {
            this.id = id;
        }
        this.upnpClass = upnpClass;
        this.title = title;
        if (upnpClass.startsWith(CLASS_ITEM)) {
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

    protected void setId(String id) {
        this.id = id;
    }

    public CdsObject getParent() {
        return parent;
    }

    protected void setParent(CdsObject parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
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

    public CdsObject getChildById(String id) {
        if (isContainer()) {
            for (CdsObject child : getChildren()) {
                if (child.getId().equals(id)) {
                    return child;
                }
            }
            CdsObject result = null;
            for (CdsObject child : getChildren()) {
                result = child.getChildById(id);
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
}
