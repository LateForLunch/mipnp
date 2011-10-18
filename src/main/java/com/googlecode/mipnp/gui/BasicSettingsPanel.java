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
 * BasicSettingsPanel.java
 * Created on Oct 18, 2011, 1:44:31 PM
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
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class BasicSettingsPanel extends JPanel {

    private MainController controller;

    public BasicSettingsPanel(MainController controller) {
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
