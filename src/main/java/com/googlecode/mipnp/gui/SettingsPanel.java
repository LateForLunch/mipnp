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
 * SettingsPanel.java
 * Created on Oct 18, 2011, 1:57:46 PM
 */
package com.googlecode.mipnp.gui;

import com.googlecode.mipnp.controller.MainController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SettingsPanel extends JPanel {

    private MainController controller;

    public SettingsPanel(MainController controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.addTab("Basic Settings", new BasicSettingsPanel(controller));
        tabbedPane.addTab("Advanced Settings", new AdvancedSettingsPanel(controller));
        add(tabbedPane, BorderLayout.CENTER);

        JPanel pnl_pageEnd = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pnl_pageEnd.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JButton btn_close = new JButton("Close");
        pnl_pageEnd.add(btn_close);
        add(pnl_pageEnd, BorderLayout.PAGE_END);
    }
}
