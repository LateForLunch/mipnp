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
import com.googlecode.mipnp.extension.ExtensionException;
import com.googlecode.mipnp.extension.ExtensionHolder;
import com.googlecode.mipnp.mediaserver.library.MediaSource;
import java.util.ArrayList;
import java.util.List;
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
    private List<ExtensionExceptionListener> listeners;

    public ExtensionsTableModel(ExtensionsController controller) {
        this.controller = controller;
        this.listeners = new ArrayList<ExtensionExceptionListener>();
    }

    public int getRowCount() {
        return controller.getNumberOfMediaSourceExtensions();
    }

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public Object getValueAt(int row, int column) {
        ExtensionHolder<MediaSource> extHolder =
                controller.getMediaSourceExtension(row);
        if (column == 0) {
            String str = "<html>";
            str += "<b>" + extHolder.getName() + "</b>";
            str += "<br />";
            str += extHolder.getDescription();
            str += "</html>";
            return str;
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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return false;
        } else if (columnIndex == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            Boolean b = (Boolean) aValue;
            try {
                if (b) {
                    controller.loadMediaSourceExtension(rowIndex);
                } else {
                    controller.unloadMediaSourceExtension(rowIndex);
                }
            } catch (ExtensionException ex) {
                fireExtensionExceptionOccurred(ex);
            }

            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    public void addExtensionExceptionListener(ExtensionExceptionListener l) {
        listeners.add(l);
    }

    public void removeExtensionExceptionListener(ExtensionExceptionListener l) {
        listeners.remove(l);
    }

    protected void fireExtensionExceptionOccurred(ExtensionException ex) {
        for (ExtensionExceptionListener l : listeners) {
            l.extensionExceptionOccurred(ex);
        }
    }
}
