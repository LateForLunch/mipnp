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
 * AbstractMainController.java
 * Created on Oct 30, 2011, 11:08:31 AM
 */
package com.googlecode.mipnp.controller;

import com.googlecode.mipnp.extension.ExtensionHolder;
import com.googlecode.mipnp.instance.SingleInstance;
import com.googlecode.mipnp.instance.SingleInstanceListener;
import com.googlecode.mipnp.mediaserver.MediaServerDevice;
import com.googlecode.mipnp.mediaserver.library.FileSystemSource;
import com.googlecode.mipnp.mediaserver.library.MediaLibrary;
import com.googlecode.mipnp.mediaserver.library.MediaServlet;
import com.googlecode.mipnp.mediaserver.library.MediaSource;
import com.googlecode.mipnp.tools.InetTools;
import com.googlecode.mipnp.upnp.UpnpServer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public abstract class AbstractMainController
        implements MainController, SingleInstanceListener {

    private static final String MEDIA_SERVLET_PATH = "/cds";

    protected Preferences prefs;
    protected ExtensionsController extensionsController;

    private UpnpServer upnpServer;
    private MediaServerDevice mediaServerDevice;
    private MediaLibrary mediaLibrary;

    public AbstractMainController(Preferences prefs) {
        this.prefs = prefs;
        try {
            prefs.loadPreferencesFromFile();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(); // TODO
        } catch (IOException ex) {
            ex.printStackTrace(); // TODO
        }
        this.extensionsController = new ExtensionsController();
    }

    public void startMediaServer() throws IOException {
        if (upnpServer != null) {
            throw new IllegalStateException("UPnP server is already running.");
        }

        this.mediaLibrary = new MediaLibrary();

        for (String mediaDir : prefs.getMediaDirectories()) {
            File mediaFile = new File(mediaDir);
            if (mediaFile.exists() && mediaFile.isDirectory()) {
                FileSystemSource fss = new FileSystemSource(mediaFile);
                mediaLibrary.addMedia(fss);
            }
        }

        Iterable<ExtensionHolder<MediaSource>> it =
                extensionsController.getMediaSourceExtensions();
        for (ExtensionHolder<MediaSource> ext : it) {
            if (ext.isLoaded()) {
                mediaLibrary.addMedia(ext.getExtensionObject());
            }
        }

        System.out.println(mediaLibrary.toString());

        this.mediaServerDevice = new MediaServerDevice(
                prefs.getUuid(), mediaLibrary);
        mediaServerDevice.setFriendlyName(prefs.getFriendlyName()+" :1");

        this.upnpServer = new UpnpServer(
                mediaServerDevice,
                NetworkInterface.getByName(prefs.getNetworkInterface()));
        
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

    public void stopMediaServer() throws IOException, InterruptedException {
        if (upnpServer != null) {
            upnpServer.stop();
            upnpServer.join();
            upnpServer = null;
        }
    }

    public void restartMediaServer() throws IOException, InterruptedException {
        stopMediaServer();
        startMediaServer();
    }

    public boolean isMediaServerRunning() {
        return (upnpServer != null);
    }

    public void exit() {
        try {
            stopMediaServer();
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
        }

        SingleInstance si = SingleInstance.getInstance();
        si.unlock();

        System.exit(0);
    }

    public String[] getNetworkInterfaceNames() throws SocketException {
        NetworkInterface[] nis = InetTools.getNetworkInterfaces();
        String[] names = new String[nis.length];
        for (int i = 0; i < nis.length; i++) {
            names[i] = nis[i].getName();
        }
        return names;
    }

    public ExtensionsController getExtensionsController() {
        return extensionsController;
    }

    public void instanceStarted(String[] args) {
        Preferences newPrefs = new Preferences(args);
        instanceStarted(newPrefs);
    }

    public abstract void instanceStarted(Preferences prefs);
}
