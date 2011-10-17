/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.mipnp.gui;

import com.googlecode.mipnp.controller.MainController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author jochem
 */
public class MediaPanel extends JPanel {

    private MainController controller;

    public MediaPanel(MainController controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        ListModel listModel = new DefaultListModel();
        JList lst_media = new JList(listModel);
        lst_media.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane listScrollPane = new JScrollPane(lst_media);
        listScrollPane.setPreferredSize(new Dimension(400, 200));
        add(listScrollPane, BorderLayout.CENTER);
    }
}
