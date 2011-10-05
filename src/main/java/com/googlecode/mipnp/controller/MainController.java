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
import com.googlecode.mipnp.gui.ConfigFrame;
import com.googlecode.mipnp.mediaserver.MediaServerDevice;
import com.googlecode.mipnp.mediaserver.library.MediaLibrary;
import com.googlecode.mipnp.mediaserver.library.MediaServlet;
import com.googlecode.mipnp.tools.InetTools;
import com.googlecode.mipnp.upnp.UpnpServer;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.UUID;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MainController {

    private static final String MEDIA_SERVLET_PATH = "/cds";

    private UpnpServer upnpServer;
    private MediaServerDevice mediaServerDevice;
    private MediaLibrary mediaLibrary;
    private Properties properties;

    public MainController(String[] args) {
        // TODO: parse args and read config file
        this.properties = new Properties();
        // Test whether or not a display is supported
        if (GraphicsEnvironment.isHeadless()) {
            MainCli mainCli = new MainCli(this);
        } else {
            try {
                // Set system look & feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // Fall back to default look & feel
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ConfigFrame frame = new ConfigFrame(MainController.this);
                }
            });
        }
    }

    public void init()
            throws SocketException, FileNotFoundException,
            IOException, ClassNotFoundException {

//        String busFactory =
//                System.getProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME);
//        System.setProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME,
//                "org.apache.cxf.bus.CXFBusFactory");

        this.mediaLibrary = new MediaLibrary();
        this.mediaServerDevice = new MediaServerDevice(getUuid(), mediaLibrary);
        this.upnpServer = new UpnpServer(
                mediaServerDevice, NetworkInterface.getByName("eth0"));

        MediaServlet mediaServlet = new MediaServlet(mediaLibrary);
        upnpServer.addServlet(mediaServlet, MEDIA_SERVLET_PATH + "/*");
    }

    public void start() throws Exception {
        try {
            upnpServer.start();
            URL mediaServletPath = new URL(
                    "http",
                    upnpServer.getHttpAddress().getHostAddress(),
                    upnpServer.getHttpPort(),
                    MEDIA_SERVLET_PATH);
            mediaServerDevice.setMediaServletPath(mediaServletPath);
        } catch (Exception ex) {
            try {
                stop();
            } catch (Exception ex1) {
            }
            throw ex;
        }
    }

    public void stop() throws Exception {
        upnpServer.stop();
        upnpServer.join();
    }

    public String[] getNetworkInterfaces() throws SocketException {
        return InetTools.getNetworkInterfaceNames();
    }

    public String getLocalHostNetworkInterface()
            throws UnknownHostException, SocketException {

        NetworkInterface ni =
                NetworkInterface.getByInetAddress(InetTools.getLocalHost());
        if (ni != null) {
            return ni.getDisplayName();
        } else {
            return "";
        }
    }

    public boolean isFirstRun() {
        return getPropertyAsBoolean("first-run", false);
    }

    public boolean isDisplayConfigOnStartup() {
        return getPropertyAsBoolean("display-config-on-startup", false);
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

    /*
     * Temp methods
     */
    private static UUID getUuid()
            throws FileNotFoundException, IOException, ClassNotFoundException {

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
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
    }

    private static void writeUuidFile(File uuidFile, UUID uuid)
            throws FileNotFoundException, IOException {

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(uuidFile));
            oos.writeObject(uuid);
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }
}
