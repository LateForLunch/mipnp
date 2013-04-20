/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010-2012  Jochem Van denbussche
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
 * ShotwellExtension.java
 * Created on Dec 14, 2012, 2:14:46 PM
 */
package com.googlecode.mipnp.extension.shotwell;

import com.googlecode.mipnp.extension.Extension;
import com.googlecode.mipnp.extension.ExtensionMethod;
import com.googlecode.mipnp.extension.ExtensionMethodType;
import com.googlecode.mipnp.mediaserver.library.MediaContainer;
import com.googlecode.mipnp.mediaserver.library.MediaSource;
import com.googlecode.mipnp.mediaserver.library.Picture;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This extension can retrieve the album information from Shotwell Photo Manager.<br/>
 * This extension has been written for Shotwell version 0.12.3 with database schema version 15.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@Extension(
        name="Shotwell",
        description="Import your Shotwell library")
public class ShotwellExtension implements MediaSource{

    private static final File DB_FILE =
            new File(System.getProperty("user.home") +
            "/Downloads/photo.db"); // TODO: refer to real file instead of test

    private static final String SELECT_PHOTOS =
            "SELECT p.filename AS file, p.title AS title, " +
            "p.event_id AS event_id, p.timestamp AS timestamp " +
            "FROM phototable AS p;";

    private static final String SELECT_EVENTS =
            "SELECT e.id AS id, e.name AS name " +
            "FROM eventtable AS e;";

    private File db;

    public ShotwellExtension() {
    }

    @ExtensionMethod(ExtensionMethodType.LOAD)
    public void loadExtension() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.db = DB_FILE;
    }

    public MediaContainer getMediaContainer() {
        MediaContainer root = new MediaContainer("Shotwell");
        if (!db.exists()) {
            return root;
        }

        Map<Integer, MediaContainer> events = getEvents();
        Calendar calendar = new GregorianCalendar();
        // Sun Nov 4, 2012
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd, yyyy", Locale.US);

        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_PHOTOS);

            while (rs.next()) {
                File file = new File(rs.getString("file"));
                String title = rs.getString("title");
                if (title == null || title.isEmpty()) {
                    title = file.getName();
                }
                int eventId = rs.getInt("event_id");
                if (eventId != -1) {
                    Picture picture = new Picture(title, file);

                    MediaContainer event = events.get(eventId);
                    if (event.getTitle() == null || event.getTitle().isEmpty()) {
                        long timestamp = rs.getLong("timestamp");
                        calendar.setTimeInMillis(timestamp * 1000);
                        event.setTitle(dateFormat.format(calendar.getTime()));
                    }
                    event.addPicture(picture);
                }
            }
        } catch (SQLException ex) {
        } finally {
            close(statement);
            close(connection);
        }

        for (MediaContainer event : events.values()) {
            root.addMediaContainer(event);
        }

        return root;
    }

    /*private List<Picture> getPictures() {
        List<Picture> pictures = new ArrayList<Picture>();
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_PHOTOS);

            while (rs.next()) {
                File file = new File(rs.getString("file"));
                String title = rs.getString("title");
                if (title == null || title.isEmpty()) {
                    title = file.getName();
                }
                Picture picture = new Picture(title, file);
                pictures.add(picture);
            }
        } catch (SQLException ex) {
        } finally {
            close(statement);
            close(connection);
        }

        return pictures;
    }*/

    private Map<Integer, MediaContainer> getEvents() {
        Map<Integer, MediaContainer> events = new HashMap<Integer, MediaContainer>();
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_EVENTS);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                if (name == null) {
                    name = "";
                }
                MediaContainer container = new MediaContainer(name);
                events.put(id, container);
            }
        } catch (SQLException ex) {
        } finally {
            close(statement);
            close(connection);
        }

        return events;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:sqlite:" + db.getAbsolutePath());
    }

    private void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
            }
        }
    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }
}
