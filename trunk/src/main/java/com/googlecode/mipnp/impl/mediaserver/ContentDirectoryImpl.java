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
 * ContentDirectoryImpl.java
 * Created on Jun 30, 2011, 3:53:50 PM
 */
package com.googlecode.mipnp.impl.mediaserver;

import com.googlecode.mipnp.upnp.ServiceImpl;
import java.io.File;
import javax.xml.ws.Holder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class ContentDirectoryImpl extends ServiceImpl implements ContentDirectory {

    private static final String XML_SERVICE_DESCRIPTION =
            "src/main/resources/mediaserver/ContentDirectory-1.xml";

    public ContentDirectoryImpl() {
        super("upnp.org", "ContentDirectory", "ContentDirectory", "1");
        try {
            parseDescription(new File(XML_SERVICE_DESCRIPTION));
        } catch (Exception ex) {
            // This should not happen
            ex.printStackTrace(); // TODO: remove line if everything seems alright
        }
    }

    public void browse(
            String objectId,
            String browseFlag,
            String filter,
            int startingIndex,
            int requestedCount,
            String sortCriteria,
            Holder<String> result,
            Holder<Integer> numberReturned,
            Holder<Integer> totalMatches,
            Holder<Integer> updateId) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void destroyObject(String objectId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getSystemUpdateId(Holder<Integer> id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getSearchCapabilities(Holder<String> searchCaps) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getSortCapabilities(Holder<String> sortCaps) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateObject(
            String objectId,
            String currentTagValue,
            String newTagValue) {

        throw new UnsupportedOperationException("Not supported yet.");
    }
}
