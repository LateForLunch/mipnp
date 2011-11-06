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
 * SearchCriteriaTokenizer.java
 * Created on Aug 7, 2011, 1:11:36 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.util.Enumeration;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class SearchCriteriaTokenizer implements Enumeration<SearchCriteriaToken> {

    private String str;
    private int index;
    private SearchCriteriaToken nextElement;

    public SearchCriteriaTokenizer(String str) {
        this.str = str.trim();
        this.index = 0;
        setNextToken();
    }

    public boolean hasMoreElements() {
        return (nextElement != null);
    }

    public SearchCriteriaToken nextElement() {
        SearchCriteriaToken token = nextElement;
        setNextToken();
        return token;
    }

    private void setNextToken() {
        String token = "";
        while (!isValidToken(token)) {
            if (index >= str.length()) {
                this.nextElement = null;
                return;
            }
            token += str.substring(index, index + 1);
            index++;
        }
        while (str.substring(index).matches("^\\s+\\S+.*")) {
            index++;
        }
        this.nextElement = new SearchCriteriaToken(token.trim());
    }

    private boolean isValidToken(String str) {
        if (str.equals("(") || str.equals(")")) {
            return true;
        } else if (str.startsWith("\"")) {
            if (str.length() >= 2 && str.endsWith("\"")) {
                return true;
            } else {
                return false;
            }
        } else if (str.matches("^\\S+\\s")) {
            return true;
        }
        return false;
    }
}
