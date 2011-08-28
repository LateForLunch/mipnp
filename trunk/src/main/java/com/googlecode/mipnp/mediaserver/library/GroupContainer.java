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
 * GroupContainer.java
 * Created on Aug 24, 2011, 3:50:27 PM
 */
package com.googlecode.mipnp.mediaserver.library;

import com.googlecode.mipnp.mediaserver.cds.CdsObject;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class GroupContainer extends CdsObject {

    private String groupProperty;
    private String childClass;
    private boolean globalSetParent;

    public GroupContainer(String id, String title, String groupProperty) {
        this(id, title, groupProperty, UPNP_CLASS_STORAGE_FOLDER);
    }

    public GroupContainer(
            String id, String title, String groupProperty, String childClass) {

        this(id, title, groupProperty, childClass, false);
    }

    public GroupContainer(
            String id, String title, String groupProperty,
            String childClass, boolean setParent) {

        super(UPNP_CLASS_STORAGE_FOLDER, id, title);
        this.groupProperty = groupProperty;
        this.childClass = childClass;
        this.globalSetParent = setParent;
    }

    @Override
    public void addChild(CdsObject child, boolean setParent) {
        String propertyValue = child.getProperty(groupProperty);
        if (propertyValue == null || propertyValue.equals("")) {
            propertyValue = "Unknown";
        }
        for (CdsObject group : getChildren()) {
            if (group.getTitle().equals(propertyValue)) {
                group.addChild(child, (globalSetParent && setParent));
                return;
            }
        }
        CdsObject group = new CdsObject(childClass);
        group.setTitle(propertyValue);
        group.addChild(child, (globalSetParent && setParent));
        super.addChild(group, true);
    }
}
