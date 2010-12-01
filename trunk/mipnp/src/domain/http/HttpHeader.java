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
 * HttpHeader.java
 * Created on Dec 1, 2010, 7:07:40 PM
 */
package domain.http;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpHeader {

    private String fieldName;
    private String fieldValue;

    public HttpHeader(String fieldName, String fieldValue) {
        setFieldName(fieldName);
        setFieldValue(fieldValue);
    }

    public HttpHeader(String str) {
        if (str == null) {
            throw new NullPointerException("Can't parse null for HttpHeader");
        }
        int index = str.indexOf(':');
        if (index < 0) {
            throw new IllegalArgumentException("Can't parse String for HttpHeader: " + str);
        }
        setFieldName(str.substring(0, index).trim());
        setFieldValue(str.substring(index + 1).trim());
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
