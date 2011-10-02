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

    private LinkedList<CdsObject> cache;
    private LinkedList<CdsObject> containers;

    public CdsObjectIterator(CdsObject start) {
        if (start.isItem()) {
            throw new IllegalArgumentException("Can't iterate an item");
        }
        this.cache = new LinkedList<CdsObject>();
        this.containers = new LinkedList<CdsObject>();
        addObjects(start);
    }

    public boolean hasNext() {
        if (cache.isEmpty() && containers.isEmpty()) {
            return false;
        }
        return true;
    }

    public CdsObject next() {
        CdsObject next = cache.poll();
        if (cache.isEmpty() && !containers.isEmpty()) {
            addObjects(containers.poll());
        }
        return next;
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }

    private void addObjects(CdsObject container) {
        for (CdsObject child : container.getChildren()) {
            cache.add(child);
            if (child.isContainer()) {
                containers.add(child);
            }
        }
    }
}
