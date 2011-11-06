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
 * SystemColorTest.java
 * Created on Nov 5, 2011, 3:37:47 PM
 */
package com.googlecode.mipnp.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.SystemColor;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SystemColorTest extends JPanel {

    private static final int OFFSET_X = 10;
    private static final int OFFSET_Y = 10;
    private static final int SPACING = 10;

    private static final int RECT_W = 60;
    private static final int RECT_H = 30;

    private static final String[] TEXT = {
        "desktop",
        "activeCaption",
        "activeCaptionText",
        "activeCaptionBorder",
        "inactiveCaption",
        "inactiveCaptionText",
        "inactiveCaptionBorder",
        "window",
        "windowBorder",
        "windowText",
        "menu",
        "menuText",
        "text",
        "textText",
        "textHighlight",
        "textHighlightText",
        "textInactiveText",
        "control",
        "controlText",
        "controlHighlight",
        "controlLtHighlight",
        "controlShadow",
        "controlDkShadow",
        "scrollbar",
        "info",
        "infoText"
    };

    private static final Color[] COLORS = {
        SystemColor.desktop,
        SystemColor.activeCaption,
        SystemColor.activeCaptionText,
        SystemColor.activeCaptionBorder,
        SystemColor.inactiveCaption,
        SystemColor.inactiveCaptionText,
        SystemColor.inactiveCaptionBorder,
        SystemColor.window,
        SystemColor.windowBorder,
        SystemColor.windowText,
        SystemColor.menu,
        SystemColor.menuText,
        SystemColor.text,
        SystemColor.textText,
        SystemColor.textHighlight,
        SystemColor.textHighlightText,
        SystemColor.textInactiveText,
        SystemColor.control,
        SystemColor.controlText,
        SystemColor.controlHighlight,
        SystemColor.controlLtHighlight,
        SystemColor.controlShadow,
        SystemColor.controlDkShadow,
        SystemColor.scrollbar,
        SystemColor.info,
        SystemColor.infoText
    };

    public SystemColorTest() {
        setPreferredSize(new Dimension(200, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = OFFSET_X;
        int y = OFFSET_Y;

        for (int i = 0; i < COLORS.length; i++) {
            g.setColor(COLORS[i]);
            g.fillRect(x, y, RECT_W, RECT_H);

            g.setColor(Color.black);
            g.drawString(TEXT[i], x + RECT_W + SPACING, y + 20);

            y += RECT_H;
            if (y >= 400) {
                x = x + RECT_W + SPACING + 250;
                y = OFFSET_Y;
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Set system look & feel
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Fall back to default look & feel
        }

        JFrame frame = new JFrame("System Color Test");
        frame.add(new SystemColorTest());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
