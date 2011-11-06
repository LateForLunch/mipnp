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
 * Preferences.java
 * Created on Nov 2, 2011, 1:04:18 PM
 */
package com.googlecode.mipnp.controller;

import com.googlecode.mipnp.tools.InetTools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Preferences {

    protected static final File PREFERENCES_FILE =
            new File("src/main/resources/mediaserver/preferences.xml");

    protected static final String PREF_UUID = "uuid";
    protected static final String PREF_NETWORK_INTERFACE = "network-interface";
    protected static final String PREF_HTTP_PORT = "http-port";
    protected static final String PREF_MEDIA_DIRS = "media-directories";
    protected static final String PREF_FRIENDLY_NAME = "friendly-name";
    protected static final String PREF_FIRST_RUN = "first-run";
    protected static final String PREF_DISPLAY_PREFERENCES = "display-preferences";

    protected Properties defaults;
    protected Properties prefsFromFile;
    protected Properties prefs;

    public Preferences(String[] args) {
        initDefaults();
        this.prefsFromFile = new Properties(defaults);
        this.prefs = new Properties(prefsFromFile);
        parseArgs(args);
    }

    public void loadPreferencesFile()
            throws FileNotFoundException, IOException {

        prefsFromFile.loadFromXML(new FileInputStream(PREFERENCES_FILE));
    }

    public void storePreferencesToFile()
            throws FileNotFoundException, IOException {

        if (!prefsFromFile.containsKey(PREF_UUID)) {
            // Set the UUID from the defaults in prefs
            prefsFromFile.setProperty(
                    PREF_UUID, defaults.getProperty(PREF_UUID));
        }
        if (!prefsFromFile.containsKey(PREF_FIRST_RUN)) {
            prefsFromFile.setProperty(PREF_FIRST_RUN, String.valueOf(false));
        }
        prefsFromFile.storeToXML(new FileOutputStream(PREFERENCES_FILE), null);
    }

    public UUID getUuid() {
        return UUID.fromString(getPreference(PREF_UUID));
    }

    public String getNetworkInterface() {
        return getPreference(PREF_NETWORK_INTERFACE);
    }

    public void setNetworkInterface(String interf) {
        setPreference(PREF_NETWORK_INTERFACE, interf);
    }

    public int getHttpPort() {
        return getPreferenceAsInt(PREF_HTTP_PORT, 0);
    }

    public void setHttpPort(int port) {
        setPreference(PREF_HTTP_PORT, String.valueOf(port));
    }

    public String[] getMediaDirectories() {
        String dirs = getPreference(PREF_MEDIA_DIRS);
        if (dirs != null) {
            return dirs.split(Pattern.quote(File.pathSeparator));
        } else {
            return new String[0];
        }
    }

    public void addMediaDirectory(String path) {
        String dirs = getPreference(PREF_MEDIA_DIRS);
        if (dirs == null) {
            dirs = "";
        }
        dirs += path;
        dirs += File.pathSeparator;
        setPreference(PREF_MEDIA_DIRS, dirs);
    }

    public void removeMediaDirectory(String path) {
        String dirs = getPreference(PREF_MEDIA_DIRS);
        if (dirs == null) {
            return;
        }
        String remove = path + File.pathSeparator;
        dirs = dirs.replaceAll(Pattern.quote(remove), "");
        setPreference(PREF_MEDIA_DIRS, dirs);
    }

    public String getFriendlyName() {
        return getPreference(PREF_FRIENDLY_NAME);
    }

    public void setFriendlyName(String friendlyName) {
        setPreference(PREF_FRIENDLY_NAME, friendlyName);
    }

    public boolean isFirstRun() {
        return getPreferenceAsBoolean(PREF_FIRST_RUN, true);
    }

    public boolean getDisplayPreferences() {
        return getPreferenceAsBoolean(PREF_DISPLAY_PREFERENCES, false);
    }

    public void setDisplayPreferences(boolean b) {
        setPreference(PREF_DISPLAY_PREFERENCES, String.valueOf(b));
    }

    protected String getPreference(String key) {
        return prefs.getProperty(key);
    }

    protected void setPreference(String key, String value) {
        // Set the pref in prefsFromFile so it gets saved in the prefs file
        prefsFromFile.setProperty(key, value);
    }

    protected boolean getPreferenceAsBoolean(String key, boolean defaultVal) {
        String value = getPreference(key);
        if (value == null) {
            return defaultVal;
        } else {
            if (value.equalsIgnoreCase("true")) {
                return true;
            } else if (value.equalsIgnoreCase("false")) {
                return false;
            } else {
                return defaultVal;
            }
        }
    }

    protected int getPreferenceAsInt(String key, int defaultVal) {
        String value = getPreference(key);
        if (value == null) {
            return defaultVal;
        } else {
            int i = defaultVal;
            try {
                i = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
            }
            return i;
        }
    }

    private void initDefaults() {
        this.defaults = new Properties();

        defaults.setProperty(PREF_UUID, UUID.randomUUID().toString());
        try {
            InetAddress localHost = InetTools.getLocalHost();
            if (localHost != null) {
                NetworkInterface ni =
                        NetworkInterface.getByInetAddress(localHost);
                if (ni != null) {
                    defaults.setProperty(
                            PREF_NETWORK_INTERFACE,
                            ni.getDisplayName());
                }
            }
        } catch (UnknownHostException ex) {
        } catch (SocketException ex) {
        }
        defaults.setProperty(PREF_HTTP_PORT, String.valueOf(0));
        defaults.setProperty(PREF_FRIENDLY_NAME, "MiPnP");
        defaults.setProperty(PREF_FIRST_RUN, String.valueOf(true));
        defaults.setProperty(PREF_DISPLAY_PREFERENCES, String.valueOf(false));
    }

    private void parseArgs(String[] args) {
        // --name (-n) NAME
        // --interface (-i) INTERFACE
        // --port (-p) PORT
        // --media (-m) DIRECTORIES
        // --preferences (-P)

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--name") || args[i].equals("-n")) {
                i++;
                prefs.setProperty(PREF_FRIENDLY_NAME, args[i]);
            } else if (args[i].equals("--interface") || args[i].equals("-i")) {
                i++;
                prefs.setProperty(PREF_NETWORK_INTERFACE, args[i]);
            } else if (args[i].equals("--port") || args[i].equals("-p")) {
                i++;
                prefs.setProperty(PREF_HTTP_PORT, args[i]);
            } else if (args[i].equals("--media") || args[i].equals("-m")) {
                i++;
                prefs.setProperty(PREF_MEDIA_DIRS, args[i]);
            } else if (args[i].equals("--preferences") || args[i].equals("-P")) {
                prefs.setProperty(PREF_DISPLAY_PREFERENCES, String.valueOf(true));
            }
        }
    }
}
