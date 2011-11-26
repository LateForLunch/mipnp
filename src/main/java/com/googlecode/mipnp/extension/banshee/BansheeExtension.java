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
package com.googlecode.mipnp.extension.banshee;

import com.googlecode.mipnp.extension.Extension;
import com.googlecode.mipnp.extension.ExtensionLoadMethod;
import com.googlecode.mipnp.mediaserver.library.MediaContainer;
import com.googlecode.mipnp.mediaserver.library.MediaSource;
import com.googlecode.mipnp.mediaserver.library.MusicAlbum;
import com.googlecode.mipnp.mediaserver.library.MusicTrack;
import com.googlecode.mipnp.mediaserver.library.Video;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@Extension(
        name="Banshee",
        description="Import your library from Banshee")
public class BansheeExtension implements MediaSource {

    private static final File DB_FILE =
            new File(System.getProperty("user.home") +
            "/.config/banshee-1/banshee.db");

    private static final String SELECT_ALBUMS_V43 =
            "SELECT al.AlbumID AS id, al.Title AS title, " +
            "al.ArtistName AS artist " +
            "FROM CoreAlbums AS al " +
            "WHERE al.Title IS NOT NULL;";

    private static final String SELECT_MUSIC_TRACKS_V43 =
            "SELECT tr.Title AS title, ar.Name AS artist, tr.AlbumID AS albumId, " +
            "tr.Genre AS genre, tr.Duration AS duration, tr.TrackNumber AS nr, " +
            "tr.BitRate AS bitRate, tr.Uri AS uri " +
            "FROM CoreTracks AS tr " +
            "LEFT JOIN CoreArtists AS ar " +
            "ON tr.ArtistID=ar.ArtistID " +
            "WHERE tr.MimeType='taglib/mp3' OR tr.MimeType='taglib/m4a';";

    private static final String SELECT_VIDEOS_V43 =
            "SELECT tr.Uri AS uri, tr.Title AS title, tr.Duration AS duration " +
            "FROM CoreTracks AS tr " +
            "WHERE tr.MimeType='taglib/avi';";

    private File db;

    public BansheeExtension() {
    }

    @ExtensionLoadMethod
    public void loadExtension() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.db = DB_FILE;
    }

    public MediaContainer getMediaContainer() {
        MediaContainer root = new MediaContainer("Banshee");

        for (MusicTrack m : getMusicTracks()) {
            root.addMusic(m);
        }
        for (Video v : getVideos()) {
            root.addVideo(v);
        }

        return root;
    }

    private List<MusicTrack> getMusicTracks() {
        List<MusicTrack> tracks = new ArrayList<MusicTrack>();
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            Map<Integer, MusicAlbum> albums = getAlbums(connection);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_MUSIC_TRACKS_V43);

            while (rs.next()) {
                try {
                    MusicTrack track = new MusicTrack();
                    track.setTitle(rs.getString("title"));
                    track.setArtist(rs.getString("artist"));
                    track.setAlbum(albums.get(rs.getInt("albumId")));
                    track.setGenre(rs.getString("genre"));
                    track.setDuration(rs.getInt("duration"));
                    track.setTrackNumber(rs.getInt("nr"));
                    track.setBitRate(rs.getInt("bitRate"));
                    track.setFile(new File(new URI(rs.getString("uri"))));
                    tracks.add(track);
                } catch (URISyntaxException ex) {
                    // Ignore record
                }
            }
        } catch (SQLException ex) {
        } finally {
            close(statement);
            close(connection);
        }

        return tracks;
    }

    private List<Video> getVideos() {
        List<Video> videos = new ArrayList<Video>();
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_VIDEOS_V43);

            while (rs.next()) {
                try {
                    Video video = new Video();
                    video.setTitle(rs.getString("title"));
                    video.setFile(new File(new URI(rs.getString("uri"))));
                    video.setDuration(1000); // TODO
                    videos.add(video);
                } catch (URISyntaxException ex) {
                    // Ignore record
                }
            }
        } catch (SQLException ex) {
        } finally {
            close(statement);
            close(connection);
        }

        return videos;
    }

    private Map<Integer, MusicAlbum> getAlbums(Connection connection)
            throws SQLException {

        Map<Integer, MusicAlbum> albums = new HashMap<Integer, MusicAlbum>();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALBUMS_V43);

            while (rs.next()) {
                MusicAlbum album = new MusicAlbum();
                album.setTitle(rs.getString("title"));
                album.setArtist(rs.getString("artist"));
                albums.put(rs.getInt("id"), album);
            }
        } finally {
            close(statement);
        }

        return albums;
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
