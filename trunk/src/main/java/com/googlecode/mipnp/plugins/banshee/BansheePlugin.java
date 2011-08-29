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

import com.googlecode.mipnp.mediaserver.library.MusicAlbum;
import com.googlecode.mipnp.mediaserver.library.MusicArtist;
import com.googlecode.mipnp.mediaserver.library.MusicGenre;
import com.googlecode.mipnp.mediaserver.library.MusicSource;
import com.googlecode.mipnp.mediaserver.library.MusicTrack;
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
public class BansheePlugin implements MusicSource {

    private static final String SELECT_ARTISTS_V43 =
            "SELECT ar.ArtistID AS id, ar.Name AS name " +
            "FROM CoreArtists AS ar;";

    private static final String SELECT_ALBUMS_V43 =
            "SELECT al.AlbumID AS id, al.Title AS title, " +
            "al.ArtistName AS artist " +
            "FROM CoreAlbums AS al;";

    private static final String SELECT_MUSIC_TRACKS_V43 =
            "SELECT tr.ArtistID AS artistId, tr.AlbumID AS albumId, " +
            "tr.Uri AS uri, tr.Title AS title, tr.Genre AS genre, "+
            "tr.TrackNumber AS nr, tr.Duration AS duration, tr.BitRate AS bitrate " +
            "FROM CoreTracks AS tr " +
            "WHERE tr.MimeType='taglib/mp3' OR tr.MimeType='taglib/m4a';";

    private File db;

    public BansheePlugin(File bansheeDb) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.db = bansheeDb;
    }

    public List<MusicTrack> getMusicTracks() {
        List<MusicTrack> tracks = new ArrayList<MusicTrack>();
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + db.getAbsolutePath());

            Map<Integer, MusicArtist> artists = getArtists(connection);
            Map<Integer, MusicAlbum> albums = getAlbums(connection);
            List<MusicGenre> genres = new ArrayList<MusicGenre>();

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_MUSIC_TRACKS_V43);
            while (rs.next()) {
                try {
                    int artistId = rs.getInt("artistId");
                    int albumId = rs.getInt("albumId");
                    File file = new File(new URI(rs.getString("uri")));
                    String title = rs.getString("title");
                    String genreStr = rs.getString("genre");
                    int trackNumber = rs.getInt("nr");
                    int duration = rs.getInt("duration");
                    int bitrate = rs.getInt("bitrate");
                    MusicTrack track = new MusicTrack(title, file);
                    MusicArtist artist = artists.get(artistId);
                    if (artist != null) {
                        track.setArtist(artist);
                    }
                    MusicAlbum album = albums.get(albumId);
                    if (album != null) {
                        track.setAlbum(album);
                    }
                    if (genreStr != null && !genreStr.equals("")) {
                        MusicGenre genre = null;
                        for (MusicGenre g : genres) {
                            if (g.getTitle().equals(genreStr)) {
                                genre = g;
                                break;
                            }
                        }
                        if (genre == null) {
                            genre = new MusicGenre(genreStr);
                            genres.add(genre);
                        }
                        track.setGenre(genre);
                    }
                    if (trackNumber > 0) {
                        track.setTrackNumber(trackNumber);
                    }
                    if (duration > 0) {
                        track.setDuration(duration);
                    }
                    if (bitrate > 0) {
                        track.setBitRate(bitrate);
                    }
                    tracks.add(track);
                } catch (URISyntaxException ex) {
                    ex.printStackTrace(); // TODO
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // TODO
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                }
            }
        }
        return tracks;
    }

    private Map<Integer, MusicArtist> getArtists(Connection connection)
            throws SQLException {

        Map<Integer, MusicArtist> artists = new HashMap<Integer, MusicArtist>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ARTISTS_V43);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                if (name != null && !name.equals("")) {
                    MusicArtist artist = new MusicArtist(name);
                    artists.put(id, artist);
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return artists;
    }

    private Map<Integer, MusicAlbum> getAlbums(Connection connection)
            throws SQLException {

        Map<Integer, MusicAlbum> albums = new HashMap<Integer, MusicAlbum>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALBUMS_V43);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artistName = rs.getString("artist");
                if (title != null && !title.equals("")) {
                    MusicAlbum album = new MusicAlbum(title);
                    album.setArtist(artistName);
                    albums.put(id, album);
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return albums;
    }

    private MusicGenre getGenre(List<MusicGenre> genres, String genre) {
        for (MusicGenre g : genres) {
            if (g.getTitle().equals(genre)) {
                return g;
            }
        }
        MusicGenre g = new MusicGenre(genre);
        genres.add(g);
        return g;
    }
}
