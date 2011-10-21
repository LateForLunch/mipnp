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
 * PreferencesPanel.java
 * Created on Oct 18, 2011, 1:57:46 PM
 */
package com.googlecode.mipnp.gui;

import com.googlecode.mipnp.controller.MainController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class PreferencesPanel extends JPanel implements ActionListener {

    private MainController controller;
    private JFrame parent;

    public PreferencesPanel(MainController controller, JFrame parent) {
        this.controller = controller;
        this.parent = parent;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        /*
         * Center
         */
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel pnl_general = new BasicPreferencesPanel(controller);
        pnl_general.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab(
                "General", pnl_general);
        JPanel pnl_advanced = new AdvancedPreferencesPanel(controller);
        pnl_advanced.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab(
                "Advanced", pnl_advanced);
        add(tabbedPane, BorderLayout.CENTER);

        /*
         * Page End
         */
        JPanel pnl_pageEnd = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pnl_pageEnd.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton btn_close = new JButton("Close");
        btn_close.setActionCommand("close");
        btn_close.addActionListener(this);
        pnl_pageEnd.add(btn_close);

        add(pnl_pageEnd, BorderLayout.PAGE_END);
    }

    public void actionPerformed(ActionEvent ae) {
        String ac = ae.getActionCommand();

        if (ac.equals("close")) {
            start();
        }
    }

    private void start() {
        try {
            controller.restart();
            parent.setVisible(false);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "An I/O error occurred while starting MiPnP\n\n" +
                    "Details: " + ex.getMessage(),
                    "An I/O Error Occurred",
                    JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException ex) {
            // This should not happen
        } catch (URISyntaxException ex) {
            // This should only happen if someone changed the UPnP service id's.
            JOptionPane.showMessageDialog(
                    this,
                    "There is probably an illegal character " +
                    "in one of the UPnP service identifiers.\n\n" +
                    "Details: " + ex.getMessage(),
                    "URI Syntax Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
