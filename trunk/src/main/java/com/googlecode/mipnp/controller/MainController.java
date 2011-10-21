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
 * MainController.java
 * Created on Oct 4, 2011, 4:58:30 PM
 */
package com.googlecode.mipnp.controller;

import com.googlecode.mipnp.cli.MainCli;
import com.googlecode.mipnp.gui.PreferencesFrame;
import com.googlecode.mipnp.mediaserver.MediaServerDevice;
import com.googlecode.mipnp.mediaserver.library.FileSystemSource;
import com.googlecode.mipnp.mediaserver.library.MediaLibrary;
import com.googlecode.mipnp.mediaserver.library.MediaServlet;
import com.googlecode.mipnp.tools.InetTools;
import com.googlecode.mipnp.upnp.UpnpServer;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MainController {

    private static final String MEDIA_SERVLET_PATH = "/cds";

    private static final String PROPERTY_FIRST_RUN = "first-run";
    private static final String PROPERTY_DISPLAY_PREFERENCES = "display-preferences";
    private static final String PROPERTY_MEDIA_DIRS = "media-dirs";
    private static final String PROPERTY_FRIENDLY_NAME = "friendly-name";
    private static final String PROPERTY_NETWORK_INTERFACE = "network-interface";
    private static final String PROPERTY_HTTP_PORT = "http-port";

    private UpnpServer upnpServer;
    private MediaServerDevice mediaServerDevice;
    private MediaLibrary mediaLibrary;
    private Properties properties;

    public MainController(String[] args) {
        this.properties = new Properties();
        init();
    }

//    public void init() {
//        String busFactory =
//                System.getProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME);
//        System.setProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME,
//                "org.apache.cxf.bus.CXFBusFactory");

//        File bansheeDb = new File("src/main/resources/banshee/banshee.db");
//        BansheePlugin bansheePlugin = new BansheePlugin(bansheeDb);
//        mediaLibrary.addMusic(bansheePlugin);
//        mediaLibrary.addVideos(bansheePlugin);

//        File mediaDir = new File("/home/jochem/ushare");
//        FileSystemSource fss = new FileSystemSource(mediaDir);
//        mediaLibrary.addMusic(fss);
//        mediaLibrary.addVideos(fss);
//        mediaLibrary.addPictures(fss);
//    }

    public void start()
            throws URISyntaxException, IOException {

        if (upnpServer != null) {
            throw new IllegalStateException("UPnP server is already running.");
        }

        this.mediaLibrary = new MediaLibrary();

        String str_mediaDirs = getProperty(PROPERTY_MEDIA_DIRS);
        if (str_mediaDirs != null) {
            String[] mediaDirs = str_mediaDirs.split(
                    Pattern.quote(File.pathSeparator));
            for (String mediaDir : mediaDirs) {
                File mediaFile = new File(mediaDir);
                if (mediaFile.exists() && mediaFile.isDirectory()) {
                    FileSystemSource fss = new FileSystemSource(mediaFile);
                    mediaLibrary.addMedia(fss);
                }
            }
        }

        this.mediaServerDevice = new MediaServerDevice(getUuid(), mediaLibrary);
        mediaServerDevice.setFriendlyName(getFriendlyName());

        this.upnpServer = new UpnpServer(
                mediaServerDevice,
                NetworkInterface.getByName(getNetworkInterface()));

        MediaServlet mediaServlet = new MediaServlet(mediaLibrary);
        upnpServer.addServlet(mediaServlet, MEDIA_SERVLET_PATH + "/*");

        upnpServer.start();

        URL mediaServletPath = new URL(
                "http",
                upnpServer.getHttpAddress().getHostAddress(),
                upnpServer.getHttpPort(),
                MEDIA_SERVLET_PATH);
        mediaServerDevice.setMediaServletPath(mediaServletPath);
    }

    public void stop() throws IOException, InterruptedException {
        if (upnpServer != null) {
            upnpServer.stop();
            upnpServer.join();
            upnpServer = null;
        }
    }

    public void restart()
            throws IOException, InterruptedException, URISyntaxException {

        stop();
        start();
    }

    public void exit() {
        try {
            stop();
        } catch (Exception ex) {
            // Nothing we can do
        }
        System.exit(0);
    }

    public String[] getNetworkInterfaceNames() throws SocketException {
        return InetTools.getNetworkInterfaceNames();
    }

    public String getLocalHostNetworkInterface()
            throws UnknownHostException, SocketException {

        NetworkInterface ni =
                NetworkInterface.getByInetAddress(InetTools.getLocalHost());
        if (ni != null) {
            return ni.getDisplayName();
        } else {
            return null;
        }
    }

    /*
     * Properties
     */
    public void addMediaDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }
        String dirs = getProperty(PROPERTY_MEDIA_DIRS);
        if (dirs == null) {
            dirs = "";
        }
        dirs += directory.getAbsolutePath();
        dirs += File.pathSeparator;
        setProperty(PROPERTY_MEDIA_DIRS, dirs);
    }

    public void removeMediaDirectory(String path) {
        String dirs = getProperty(PROPERTY_MEDIA_DIRS);
        if (dirs == null) {
            return;
        }
        String remove = path + File.pathSeparator;
        dirs = dirs.replaceAll(Pattern.quote(remove), "");
        setProperty(PROPERTY_MEDIA_DIRS, dirs);
    }

    public String getFriendlyName() {
        return getProperty(PROPERTY_FRIENDLY_NAME);
    }

    public void setFriendlyName(String friendlyName) {
        setProperty(PROPERTY_FRIENDLY_NAME, friendlyName);
    }

    public String getNetworkInterface() {
        return getProperty(PROPERTY_NETWORK_INTERFACE);
    }

    public void setNetworkInterface(String interf) {
        setProperty(PROPERTY_NETWORK_INTERFACE, interf);
    }

    public int getHttpPort() {
        String prop = getProperty(PROPERTY_HTTP_PORT);
        int port = 0;
        try {
            port = Integer.parseInt(prop);
        } catch (NumberFormatException ex) {
        }
        return port;
    }

    public void setHttpPort(int port) {
        setProperty(PROPERTY_HTTP_PORT, String.valueOf(port));
    }

    public boolean getDisplayPreferences() {
        return getPropertyAsBoolean(PROPERTY_DISPLAY_PREFERENCES, false);
    }

    public void setDisplayPreferences(boolean displayPreferences) {
        setProperty(PROPERTY_DISPLAY_PREFERENCES,
                String.valueOf(displayPreferences));
    }

    public boolean isFirstRun() {
        return getPropertyAsBoolean(PROPERTY_FIRST_RUN, true);
    }

    protected boolean getPropertyAsBoolean(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        } else {
            if (value.equalsIgnoreCase("true")) {
                return true;
            } else if (value.equalsIgnoreCase("false")) {
                return false;
            } else {
                return defaultValue;
            }
        }
    }

    protected String getProperty(String key) {
        return properties.getProperty(key);
    }

    protected void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    protected void loadProperties() {
        // TODO
    }

    protected void storeProperties() {
        // TODO
    }

    private void init() {
        // TODO: parse args and read config file

        // Test whether or not a display is supported
        if (GraphicsEnvironment.isHeadless()) {
            MainCli mainCli = new MainCli(this);
        } else {
            try {
                // Set system look & feel
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // Fall back to default look & feel
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    PreferencesFrame frame =
                            new PreferencesFrame(MainController.this);
                }
            });
        }
    }

    /*
     * Temp methods
     */
    private static UUID getUuid() {
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
