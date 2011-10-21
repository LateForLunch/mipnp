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
 * BasicPreferencesPanel.java
 * Created on Oct 18, 2011, 1:44:31 PM
 */
package com.googlecode.mipnp.gui;

import com.googlecode.mipnp.controller.MainController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class BasicPreferencesPanel extends JPanel implements ActionListener {

    private MainController controller;
    private DefaultListModel listModel;
    private JList lst_media;

    public BasicPreferencesPanel(MainController controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        setLayout(new BorderLayout(10, 10));

        JLabel lbl_info = new JLabel("These directories will be shared:");
        add(lbl_info, BorderLayout.PAGE_START);

        this.listModel = new DefaultListModel();
        this.lst_media = new JList(listModel);
        lst_media.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane listScrollPane = new JScrollPane(lst_media);
        listScrollPane.setPreferredSize(new Dimension(400, 200));
        add(listScrollPane, BorderLayout.CENTER);

        JButton btn_add = new JButton("Add...");
        btn_add.setActionCommand("add");
        btn_add.addActionListener(this);

        JLabel lbl_space = new JLabel();
        lbl_space.setPreferredSize(new Dimension(10, 0));

        JButton btn_remove = new JButton("Remove");
        btn_remove.setActionCommand("remove");
        btn_remove.addActionListener(this);

        JPanel pnl_pageEnd = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnl_pageEnd.add(btn_add);
        pnl_pageEnd.add(lbl_space);
        pnl_pageEnd.add(btn_remove);
        add(pnl_pageEnd, BorderLayout.PAGE_END);
    }

    public void actionPerformed(ActionEvent ae) {
        String ac = ae.getActionCommand();

        if (ac.equals("add")) {
            addDirectory();
        } else if (ac.equals("remove")) {
            removeDirectory();
        }
    }

    private void addDirectory() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Choose the directory you want to share.");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(false);

        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            listModel.addElement(file.getAbsolutePath());
            controller.addMediaDirectory(file.getAbsolutePath());
        }
    }

    private void removeDirectory() {
        int i = lst_media.getSelectedIndex();
        if (i >= 0 && i < listModel.size()) {
            String remove = (String) listModel.remove(i);
            controller.removeMediaDirectory(remove);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "You must first select a directory to remove.",
                    "Select a directory to remove",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
