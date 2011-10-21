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
 * AdvancedPreferencesPanel.java
 * Created on Oct 5, 2011, 3:34:32 PM
 */
package com.googlecode.mipnp.gui;

import com.googlecode.mipnp.controller.MainController;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.SocketException;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class AdvancedPreferencesPanel
extends JPanel implements PreferencesCloseListener {

    private static final Insets DEFAULT_INSETS = new Insets(5, 5, 5, 5);

    private MainController controller;
    private JTextField txt_name;
    private JComboBox cmb_networkinterface;
    private JTextField txt_port;
    private JCheckBox chk_displayWindow;

    public AdvancedPreferencesPanel(MainController controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lbl_name = new JLabel("Friendly Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = DEFAULT_INSETS;
        add(lbl_name, gbc);

        JLabel lbl_networkinterface = new JLabel("Network Interface:");
        gbc.gridy = 1;
        add(lbl_networkinterface, gbc);

        JLabel lbl_port = new JLabel("HTTP Port:");
        gbc.gridy = 2;
        add(lbl_port, gbc);

        this.txt_name = new JTextField(controller.getFriendlyName(), 12);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0.5;
        add(txt_name, gbc);

        String[] nis;
        try {
            nis = controller.getNetworkInterfaceNames();
        } catch (SocketException ex) {
            nis = new String[] {"An error occurred"};
            displayError(ex.getMessage());
        }
        this.cmb_networkinterface = new JComboBox(nis);
        cmb_networkinterface.setSelectedItem(
                controller.getNetworkInterface());
        gbc.gridy = 1;
        add(cmb_networkinterface, gbc);

        this.txt_port = new JTextField(
                String.valueOf(controller.getHttpPort()));
        gbc.gridy = 2;
        add(txt_port, gbc);

        this.chk_displayWindow = new JCheckBox("Display this window on startup");
        if (!controller.isFirstRun() && controller.getDisplayPreferences()) {
            chk_displayWindow.setSelected(true);
        }
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.0;
        add(chk_displayWindow, gbc);
    }

    public void preferencesClosing() {
        controller.setFriendlyName(txt_name.getText());
        controller.setNetworkInterface(
                (String) cmb_networkinterface.getSelectedItem());
        try {
            int port = Integer.parseInt(txt_port.getText());
            controller.setHttpPort(port);
        } catch (NumberFormatException ex) {
        }
        controller.setDisplayPreferences(chk_displayWindow.isSelected());
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(
                this,
                "An error occurred.\n" +
                "Details: " + message,
                "MiPnP - An Error Occurred",
                JOptionPane.ERROR_MESSAGE);
    }
}
