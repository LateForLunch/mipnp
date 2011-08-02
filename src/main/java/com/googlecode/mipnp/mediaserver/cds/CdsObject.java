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

    private static int nextId = 0;

    private int id;
    private String upnpClass;
    private CdsObject parent;
    private String title;
    private boolean container;
    private List<CdsObject> children;
    private Resource resource;

    public CdsObject(String upnpClass, String title) {
        if (!upnpClass.startsWith(CLASS_ITEM) && !upnpClass.startsWith(CLASS_CONTAINER)) {
            throw new IllegalArgumentException(
                    "UPnP Class must start with \"" +
                    CLASS_ITEM + "\" or \"" + CLASS_CONTAINER + "\".");
        }
        this.id = nextId;
        nextId++;
        this.upnpClass = upnpClass;
        this.title = title;
        if (upnpClass.startsWith(CLASS_ITEM)) {
            this.container = false;
        } else {
            this.container = true;
            this.children = new ArrayList<CdsObject>();
        }
    }

    public int getId() {
        return id;
    }

    public String getUpnpClass() {
        return upnpClass;
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

    public CdsObject getChildById(int id) {
        if (isContainer()) {
            for (CdsObject child : children) {
                if (child.getId() == id) {
                    return child;
                }
            }
            CdsObject result = null;
            for (CdsObject child : children) {
                result = child.getChildById(id);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public Iterable<CdsObject> getChildren() {
        return children;
    }

//    public InputStream getInputStream() {
//        return null;
//    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
