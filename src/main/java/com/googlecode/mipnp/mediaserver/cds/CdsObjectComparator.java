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
 * CdsObjectComparator.java
 * Created on Aug 27, 2011, 1:53:44 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.util.Comparator;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class CdsObjectComparator implements Comparator<CdsObject> {

    private String[] sortCriteria;

    public CdsObjectComparator(String sortCriteria) {
        this.sortCriteria = sortCriteria.split("(?<!\\\\),");
        for (int i = 0; i < this.sortCriteria.length; i++) {
            this.sortCriteria[i] = this.sortCriteria[i].replaceAll("\\\\,", ",");
        }
    }

    public int compare(CdsObject o1, CdsObject o2) {
        for (int i = 0; i < sortCriteria.length; i++) {
            String propertyVal1 = o1.getProperty(getProperty(sortCriteria[i]));
            String propertyVal2 = o2.getProperty(getProperty(sortCriteria[i]));
            if (propertyVal1 == null) {
                propertyVal1 = "";
            }
            if (propertyVal2 == null) {
                propertyVal2 = "";
            }
            int compare;
            if (getSign(sortCriteria[i]).equals("+")) {
                compare = propertyVal1.compareTo(propertyVal2);
            } else {
                compare = propertyVal2.compareTo(propertyVal1);
            }
            if (compare != 0) {
                return compare;
            }
        }
        return 0;
    }

    protected String getProperty(String sortCriteriaPart) {
        if (sortCriteriaPart.startsWith("+") || sortCriteriaPart.startsWith("-")) {
            return sortCriteriaPart.substring(1);
        }
        return sortCriteriaPart;
    }

    protected String getSign(String sortCriteriaPart) {
        if (sortCriteriaPart.startsWith("+") || sortCriteriaPart.startsWith("-")) {
            return sortCriteriaPart.substring(0, 1);
        }
        return "+";
    }
}
