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
 * MediaLibrary.java
 * Created on Jul 27, 2011, 1:42:13 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MediaLibrary {

//    private CdsObject root;

    public MediaLibrary(File mediaDirectory) {
//        this.root = new StorageFolder(rootDirectory);
//        root.setId("0");
    }

//    public CdsObject getRootObject() {
//        return root;
//    }

    public CdsObject getObjectById(String id) {
//        return root.getChildById(id);
        return null;
    }

    public List<CdsObject> search(SearchCriteria sc) {
//        return search(sc, root);
        return null;
    }

    private List<CdsObject> search(SearchCriteria sc, CdsObject obj) {
        List<CdsObject> result = new ArrayList<CdsObject>();
        if (sc.meetsCriteria(obj)) {
            result.add(obj);
        }
        if (obj.isContainer()) {
            for (CdsObject child : obj.getChildren()) {
                result.addAll(search(sc, child));
            }
        }
        return result;
    }
}
