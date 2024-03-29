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
 * GuiController.java
 * Created on Oct 30, 2011, 11:29:47 AM
 */
package com.googlecode.mipnp.controller;

import com.googlecode.mipnp.view.gui.PreferencesView;
import com.googlecode.mipnp.view.statusicon.ServerStatus;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class GuiController extends AbstractMainController {

    private PreferencesView view;
    private ServerStatus status;

    public GuiController(Preferences prefs) {
        super(prefs);
        setSystemLookAndFeel();
        this.view = new PreferencesView(this, prefs);

        try {
            Image img = ImageIO.read(GuiController.class.getResourceAsStream(
                    "/mediaserver/juk_48x48.png"));
            this.status = new ServerStatus(this, img);
        } catch (IOException ex) {
            // This should not happen
            ex.printStackTrace();
        }

        if (prefs.isFirstRun() || prefs.getDisplayPreferences()) {
            createView();
        } else {
            view.startMediaServer();
        }

        status.addInTray();
    }

    @Override
    public void startMediaServer() throws IOException {
        super.startMediaServer();
        status.updateStatus();
    }

    @Override
    public void stopMediaServer() throws IOException, InterruptedException {
        super.stopMediaServer();
        status.updateStatus();
    }

    @Override
    public void instanceStarted(Preferences prefs) {
        if (prefs.isStopCommand()) {
            view.stopMediaServer();
        } else if (prefs.getDisplayPreferences()) {
            if (!view.isShowingView()) {
                createView();
            }
        }
    }

    public void displayConfiguration(){
        if (!view.isShowingView()) {
            createView();
        }
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Fall back to default look & feel
        }
    }

    private void createView() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                view.createView();
            }
        });
    }
}
