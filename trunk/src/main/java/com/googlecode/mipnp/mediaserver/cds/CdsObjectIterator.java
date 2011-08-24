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
 * CdsObjectIterator.java
 * Created on Aug 24, 2011, 4:17:38 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class CdsObjectIterator implements Iterator<CdsObject> {

    private LinkedList<CdsObject> objects;

    public CdsObjectIterator(CdsObject start) {
        if (start.isItem()) {
            throw new IllegalArgumentException("Can't iterate an item");
        }
        this.objects = new LinkedList<CdsObject>();
        addObjects(start);
    }

    public boolean hasNext() {
        return !objects.isEmpty();
    }

    public CdsObject next() {
        return objects.poll();
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }

    private void addObjects(CdsObject container) {
        objects.addAll(container.getChildren());
        for (CdsObject obj : container.getChildren()) {
            if (obj.isContainer()) {
                addObjects(obj);
            }
        }
    }
}
