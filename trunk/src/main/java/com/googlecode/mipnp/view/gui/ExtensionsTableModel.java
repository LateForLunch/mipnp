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
 * ExtensionsTableModel.java
 * Created on Nov 20, 2011, 11:37:58 AM
 */
package com.googlecode.mipnp.view.gui;

import com.googlecode.mipnp.controller.ExtensionsController;
import com.googlecode.mipnp.extension.ExtensionHolder;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ExtensionsTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = new String[] {
        "Extension",
        "Enabled"
    };

    private ExtensionsController controller;

    public ExtensionsTableModel(ExtensionsController controller) {
        this.controller = controller;
    }

    public int getRowCount() {
        return controller.getNumberOfExtensions();
    }

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public Object getValueAt(int row, int column) {
        ExtensionHolder<?> extHolder = controller.getExtension(row);
        if (column == 0) {
            return extHolder.getName() + "\n" + extHolder.getDescription();
        } else if (column == 1) {
            return extHolder.isLoaded();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return String.class;
        } else if (columnIndex == 1) {
            return Boolean.class;
        }
        return null;
    }
}
