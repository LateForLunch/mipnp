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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Preferences {

    protected static final String PREF_FIRST_RUN = "first-run";
    protected static final String PREF_DISPLAY_PREFERENCES = "display-preferences";
    protected static final String PREF_MEDIA_DIRS = "media-directories";
    protected static final String PREF_FRIENDLY_NAME = "friendly-name";
    protected static final String PREF_NETWORK_INTERFACE = "network-interface";
    protected static final String PREF_HTTP_PORT = "http-port";
    protected static final String PREF_UUID = "uuid";

    protected Properties prefs;

    public Preferences() {
        this.prefs = new Properties();
    }

    public String[] getMediaDirectories() {
        String str_mediaDirs = getPreference(PREF_MEDIA_DIRS);
        if (str_mediaDirs != null) {
            return str_mediaDirs.split(Pattern.quote(File.pathSeparator));
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

    public UUID getUuid() {
        return getUuidFromFile(); // TODO
    }

    public boolean getDisplayPreferences() {
        return getPreferenceAsBoolean(PREF_DISPLAY_PREFERENCES, false);
    }

    public void setDisplayPreferences(boolean b) {
        setPreference(PREF_DISPLAY_PREFERENCES, String.valueOf(b));
    }

    public boolean isFirstRun() {
        return getPreferenceAsBoolean(PREF_FIRST_RUN, true);
    }

    protected String getPreference(String key) {
        return prefs.getProperty(key);
    }

    protected void setPreference(String key, String value) {
        prefs.setProperty(key, value);
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

    protected void loadPreferences() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void storePreferences() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * Temp methods
     */
    private static UUID getUuidFromFile() {
        File uuidFile = new File("src/main/resources/mediaserver/uuid.object");
        if (!uuidFile.exists()) {
            UUID uuid = UUID.randomUUID();
            writeUuidFile(uuidFile, uuid);
            return uuid;
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(uuidFile));
            return (UUID) ois.readObject();
        } catch (Exception ex) {
            return UUID.randomUUID();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private static void writeUuidFile(File uuidFile, UUID uuid) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(uuidFile));
            oos.writeObject(uuid);
        } catch (IOException ex) {
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                }
            }
        }
    }
}
