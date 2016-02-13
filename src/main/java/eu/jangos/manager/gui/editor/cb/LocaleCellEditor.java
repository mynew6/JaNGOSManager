/*
 * Copyright 2016 Warkdev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.jangos.manager.gui.editor.cb;

import eu.jangos.manager.gui.renderer.cb.ListLocaleCellRenderer;
import eu.jangos.manager.model.Locale;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Warkdev
 * @version 1.0
 * @since 12-02-2016
 */
public class LocaleCellEditor extends AbstractCellEditor 
        implements TableCellEditor, ActionListener, FocusListener {

    private final List<Locale> listLocales;
    private Locale selectedLocale;

    public LocaleCellEditor(List<Locale> listLocales) {
        this.listLocales = listLocales;
        this.selectedLocale = null;
    }        
    
    @Override
    public Object getCellEditorValue() {
        return this.selectedLocale;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if(value instanceof Locale) {
            this.selectedLocale = (Locale) value;
        }
        
        JComboBox<Locale> comboLocale = new JComboBox<>();
        comboLocale.setRenderer(new ListLocaleCellRenderer());
        
        for (Locale l : listLocales) {
            comboLocale.addItem(l);
        }
        
        comboLocale.setSelectedItem(this.selectedLocale);
        comboLocale.addActionListener(this);
        comboLocale.addFocusListener(this);
        
        if (isSelected) {
            comboLocale.setBackground(table.getSelectionBackground());
        } else {
            comboLocale.setBackground(table.getSelectionForeground());
        }

        return comboLocale;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<Locale> comboLocale = (JComboBox<Locale>) e.getSource();        
        this.selectedLocale = (Locale) comboLocale.getSelectedItem();        
    }

    @Override
    public void focusGained(FocusEvent e) {
        return;
    }

    @Override
    public void focusLost(FocusEvent e) {
        this.stopCellEditing();
    }
    
}
