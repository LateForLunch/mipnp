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

package http;

import domain.http.HttpHeader;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author jochem
 */
public class HttpHeaderTest {

    private static final String PARSE_1 = "Content-Length: 348";
    private static final String PARSE_2 = "Content-Length 348";

    @Test
    public void parse1() {
        HttpHeader h = new HttpHeader(PARSE_1);
        Assert.assertEquals("Content-Length", h.getFieldName());
        Assert.assertEquals("348", h.getFieldValue());
    }

    @Test (expected=IllegalArgumentException.class)
    public void parse2() {
        new HttpHeader(PARSE_2);
    }
}
