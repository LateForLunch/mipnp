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
 * BansheePlugin.java
 * Created on Aug 25, 2011, 3:07:45 PM
 */
package com.googlecode.mipnp.plugins.banshee;

import com.googlecode.mipnp.mediaserver.cds.CdsConstants;
import com.googlecode.mipnp.mediaserver.cds.CdsObject;
import com.googlecode.mipnp.mediaserver.cds.CdsObjectFactory;
import com.googlecode.mipnp.mediaserver.cds.MediaSource;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class BansheePlugin implements MediaSource {

    private static final String SQL_SELECT =
            "SELECT tr.Uri AS uri, tr.Title AS title, ar.Name AS artist, al.Title AS album " +
            "FROM CoreTracks AS tr " +
            "LEFT JOIN CoreArtists AS ar " +
            "ON tr.ArtistID=ar.ArtistID " +
            "LEFT JOIN CoreAlbums AS al " +
            "ON tr.AlbumID=al.AlbumID";

    private File db;

    public BansheePlugin(File bansheeDb) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.db = bansheeDb;
    }

    public List<CdsObject> getItems() {
        List<CdsObject> list = new ArrayList<CdsObject>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + db.getAbsolutePath());
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery(SQL_SELECT);
            while (rs.next()) {
                try {
                    File file = new File(new URI(rs.getString("uri")));
                    CdsObject obj = CdsObjectFactory.createItem(file);
                    if (obj == null) {
                        continue;
                    }
                    obj.setTitle(replaceIllegalChars(rs.getString("title")));
                    obj.setProperty(
                            CdsConstants.PROPERTY_ARTIST,
                            replaceIllegalChars(rs.getString("artist")));
                    obj.setProperty(
                            CdsConstants.PROPERTY_ALBUM,
                            replaceIllegalChars(rs.getString("album")));
                    list.add(obj);
                } catch (URISyntaxException ex) {
                    ex.printStackTrace(); // TODO
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // TODO
        }
        return list;
    }

    private String replaceIllegalChars(String str) {
        if (str != null) {
            return str.replaceAll("&", "&amp;");
        }
        return str;
    }
}
