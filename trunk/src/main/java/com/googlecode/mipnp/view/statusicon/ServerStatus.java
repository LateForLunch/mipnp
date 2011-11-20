package com.googlecode.mipnp.view.statusicon;

import com.googlecode.mipnp.controller.MainController;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Pierre-Luc Plourde <pierrelucplourde2@gmail.com>
 */
public class ServerStatus {

    private MainController controller;
    private JPopupMenu menu;
    private JXTrayIcon sysTrayIcon;
    private JMenuItem mi_startStop;

    public ServerStatus(MainController controller, Image img) {
        this.controller = controller;
        this.menu = new JPopupMenu("Menu");
        this.sysTrayIcon = new JXTrayIcon(img);
        JMenuItem mi_prefs = new JMenuItem("Preferences");
        this.mi_startStop = new JMenuItem();
        JMenuItem mi_exit = new JMenuItem("Exit");

        updateStatus();

        mi_prefs.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ServerStatus.this.controller.displayConfiguration();
            }
        });

        mi_startStop.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    if (ServerStatus.this.controller.isMediaServerRunning()) {
                        ServerStatus.this.controller.stopMediaServer();
                    } else {
                        ServerStatus.this.controller.startMediaServer();
                    }
                    updateStatus();
                } catch (IOException ex) {
                    Logger.getLogger(
                            ServerStatus.class.getName()).log(
                            Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(
                            ServerStatus.class.getName()).log(
                            Level.SEVERE, null, ex);
                }

            }
        });

        mi_exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ServerStatus.this.controller.exit();
            }
        });

        menu.add(mi_prefs);
        menu.add(mi_startStop);
        menu.addSeparator();
        menu.add(mi_exit);

        sysTrayIcon.setJPopupMenu(menu);
    }

    public void addInTray() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            try {
                tray.add(sysTrayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        }
    }

    public void updateStatus() {
        if (controller.isMediaServerRunning()) {
            mi_startStop.setText("Stop");
            sysTrayIcon.setToolTip("MiPnP is sharing your media");
        } else {
            mi_startStop.setText("Start");
            sysTrayIcon.setToolTip("MiPnP is not sharing your media");
        }
    }
}
