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
 * PreferencesView.java
 * Created on Nov 2, 2011, 12:51:41 PM
 */
package com.googlecode.mipnp.view.gui;

import com.googlecode.mipnp.controller.MainController;
import com.googlecode.mipnp.controller.Preferences;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class PreferencesView implements ActionListener {

    private static final Insets DEFAULT_INSETS = new Insets(5, 5, 5, 5);

    private MainController controller;
    private Preferences prefs;
    private boolean showingView;

    private JFrame frame;

    private JPanel pnl_main;
    private JButton btn_close;

    private JPanel pnl_general;
    private DefaultListModel mediaListModel;
    private JList lst_media;
    private JButton btn_addMedia;
    private JButton btn_removeMedia;

    private JPanel pnl_advanced;
    private JTextField txt_friendlyName;
    private JComboBox cmb_networkInterface;
    private JTextField txt_httpPort;
    private JCheckBox chk_displayPrefs;

    private JPanel pnl_extensions;

    public PreferencesView(MainController controller, Preferences prefs) {
        this.controller = controller;
        this.prefs = prefs;
        this.showingView = false;
    }

    public boolean isShowingView() {
        return showingView;
    }

    public void createView() {
        this.frame = new JFrame("MiPnP - Preferences");

        createMainPanel();
        frame.add(pnl_main);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                savePrefs();
//                startMediaServer();
                frame.dispose();
                showingView = false;
                stopMediaServer(); // TODO: start instead of stop
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.showingView = true;
    }

    private void createMainPanel() {
        this.pnl_main = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        createGeneralPanel();
        pnl_general.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("General", pnl_general);

        createAdvancedPanel();
        pnl_advanced.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Advanced", pnl_advanced);

        createExtensionsPanel();
        pnl_extensions.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Extensions", pnl_extensions);

        pnl_main.add(tabbedPane, BorderLayout.CENTER);

        JPanel pnl_pageEnd = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pnl_pageEnd.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        this.btn_close = new JButton("Close");
        btn_close.addActionListener(this);
        pnl_pageEnd.add(btn_close);

        pnl_main.add(pnl_pageEnd, BorderLayout.PAGE_END);
    }

    private void createGeneralPanel() {
        this.pnl_general = new JPanel(new BorderLayout(10, 10));

        JLabel lbl_info = new JLabel("These directories will be shared:");
        pnl_general.add(lbl_info, BorderLayout.PAGE_START);

        this.mediaListModel = new DefaultListModel();
        for (String mediaDir : prefs.getMediaDirectories()) {
            mediaListModel.addElement(mediaDir);
        }

        this.lst_media = new JList(mediaListModel);
        lst_media.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane listScrollPane = new JScrollPane(lst_media);
        listScrollPane.setPreferredSize(new Dimension(400, 200));
        pnl_general.add(listScrollPane, BorderLayout.CENTER);

        this.btn_addMedia = new JButton("Add...");
        btn_addMedia.addActionListener(this);

        JLabel lbl_space = new JLabel();
        lbl_space.setPreferredSize(new Dimension(10, 0));

        this.btn_removeMedia = new JButton("Remove");
        btn_removeMedia.addActionListener(this);

        JPanel pnl_pageEnd = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnl_pageEnd.add(btn_addMedia);
        pnl_pageEnd.add(lbl_space);
        pnl_pageEnd.add(btn_removeMedia);
        pnl_general.add(pnl_pageEnd, BorderLayout.PAGE_END);
    }

    private void createAdvancedPanel() {
        this.pnl_advanced = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lbl_name = new JLabel("Friendly Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = DEFAULT_INSETS;
        pnl_advanced.add(lbl_name, gbc);

        JLabel lbl_networkinterface = new JLabel("Network Interface:");
        gbc.gridy = 1;
        pnl_advanced.add(lbl_networkinterface, gbc);

        JLabel lbl_port = new JLabel("HTTP Port:");
        gbc.gridy = 2;
        pnl_advanced.add(lbl_port, gbc);

        this.txt_friendlyName = new JTextField(prefs.getFriendlyName(), 12);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0.5;
        pnl_advanced.add(txt_friendlyName, gbc);

        NetworkInterface[] nis;
        try {
            nis = controller.getNetworkInterfaceNames();
            this.cmb_networkInterface = new JComboBox(nis);

            this.cmb_networkInterface.setRenderer(new DefaultListCellRenderer() {
                   @Override
                   public Component getListCellRendererComponent(
                       JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                       super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                       if (value instanceof NetworkInterface) {
                           setText(((NetworkInterface)value).getDisplayName());
                       }
                       return this;
                   }
               });

            cmb_networkInterface.setSelectedItem(NetworkInterface.getByName(prefs.getNetworkInterface()));
        } catch (SocketException ex) {
            String[] message = new String[] {"An error occurred"};
            this.cmb_networkInterface = new JComboBox(message);

            displayError(
                    "An Error Occurred",
                    "An error occurred while getting the " +
                    "available network interfaces.", ex);
        }
        
        gbc.gridy = 1;
        pnl_advanced.add(cmb_networkInterface, gbc);

        this.txt_httpPort = new JTextField(
                String.valueOf(prefs.getHttpPort()));
        gbc.gridy = 2;
        pnl_advanced.add(txt_httpPort, gbc);

        this.chk_displayPrefs = new JCheckBox("Display this window on startup");
        if (!prefs.isFirstRun() && prefs.getDisplayPreferences()) {
            chk_displayPrefs.setSelected(true);
        }
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.0;
        pnl_advanced.add(chk_displayPrefs, gbc);
    }

    private void createExtensionsPanel() {
        this.pnl_extensions = new JPanel(new BorderLayout(10, 10));

        JTable tbl_extensions = new JTable();
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btn_close) {
            savePrefs();
            startMediaServer();
            frame.dispose();
            showingView = false;
        } else if (event.getSource() == btn_addMedia) {
            addMediaDirectory();
        } else if (event.getSource() == btn_removeMedia) {
            removeMediaDirectory();
        }
    }

    private void savePrefs() {
        prefs.setFriendlyName(txt_friendlyName.getText());
        prefs.setNetworkInterface(
                ((NetworkInterface) cmb_networkInterface.getSelectedItem()).getName());
        try {
            int port = Integer.parseInt(txt_httpPort.getText());
            prefs.setHttpPort(port);
        } catch (NumberFormatException ex) {
        }
        prefs.setDisplayPreferences(chk_displayPrefs.isSelected());

        try {
            prefs.storePreferencesToFile();
        } catch (IOException ex) {
            // Ignore - preferences window will be shown again next time
        }
    }

    public void startMediaServer() {
        try {
            controller.restartMediaServer();
        } catch (IOException ex) {
            displayError(
                    "An I/O Error Occurred",
                    "An I/O error occurred while starting MiPnP", ex);
        } catch (InterruptedException ex) {
            // This should not happen
        }
    }

    public void stopMediaServer() {
        try {
            controller.stopMediaServer();
        } catch (IOException ex) {
            displayError(
                    "An I/O Error Occurred",
                    "An I/O error occurred while stopping MiPnP", ex);
        } catch (InterruptedException ex) {
            // This should not happen
        }
        controller.exit();
    }

    private void addMediaDirectory() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Choose the directory you want to share.");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(false);

        int returnVal = fc.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            mediaListModel.addElement(file.getAbsolutePath());
            prefs.addMediaDirectory(file.getAbsolutePath());
        }
    }

    private void removeMediaDirectory() {
        int i = lst_media.getSelectedIndex();
        if (i >= 0 && i < mediaListModel.size()) {
            String remove = (String) mediaListModel.remove(i);
            prefs.removeMediaDirectory(remove);
        } else {
            displayError(
                    "Select a directory to remove",
                    "You must first select a directory to remove.", null);
        }
    }

    private void displayError(String title, String message, Exception ex) {
        if (ex != null) {
            message += "\n\nDetails: ";
            message += ex.getMessage();
        }

        JOptionPane.showMessageDialog(
                frame,
                message,
                title,
                JOptionPane.ERROR_MESSAGE);
    }
}
