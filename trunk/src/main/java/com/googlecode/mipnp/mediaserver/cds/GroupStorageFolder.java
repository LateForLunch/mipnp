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
 * GroupStorageFolder.java
 * Created on Aug 24, 2011, 3:50:27 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class GroupStorageFolder extends CdsObject {

    private String groupProperty;

    public GroupStorageFolder(String id, String title, String groupProperty) {

        super(UPNP_CLASS_STORAGE_FOLDER, id, title);
        this.groupProperty = groupProperty;
    }

    @Override
    public void addChild(CdsObject child) {
        addChild(child, false);
    }

    @Override
    public void addChild(CdsObject child, boolean setParent) {
        String property = child.getProperty(groupProperty);
        for (CdsObject group : getChildren()) {
            if (group.getTitle().equals(property)) {
                group.addChild(child, setParent);
                return;
            }
        }
        CdsObject group = CdsObjectFactory.createStorageFolder();
        group.setTitle(property);
        group.addChild(child, setParent);
        super.addChild(group, true);
    }
}
