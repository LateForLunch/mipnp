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
 * MultiLineTableCellRenderer.java
 * Created on Dec 2, 2011, 4:46:16 PM
 */
package com.googlecode.mipnp.view.gui;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MultiLineTableCellRenderer extends DefaultTableCellRenderer {

    public MultiLineTableCellRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        int rowHeight = c.getPreferredSize().height;
        if (rowHeight <= 0) {
            rowHeight = table.getRowHeight(row);
        }
        table.setRowHeight(row, rowHeight);

        return c;
    }
}
