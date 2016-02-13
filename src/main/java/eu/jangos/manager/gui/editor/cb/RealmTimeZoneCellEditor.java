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

import eu.jangos.manager.gui.renderer.cb.ListTimezoneCellRenderer;
import eu.jangos.manager.model.Realmtimezone;
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
 * @since 13-02-2016
 */
public class RealmTimeZoneCellEditor extends AbstractCellEditor 
        implements TableCellEditor, ActionListener, FocusListener {

    private final List<Realmtimezone> listTimezones;
    private Realmtimezone selectedTimezone;

    public RealmTimeZoneCellEditor(List<Realmtimezone> listTimezones) {
        this.listTimezones = listTimezones;
        this.selectedTimezone = listTimezones.get(0);        
    }        
    
    @Override
    public Object getCellEditorValue() {        
        return this.selectedTimezone;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {                   
        if(value instanceof Realmtimezone) {
            this.selectedTimezone = (Realmtimezone) value;
        }                                
        
        JComboBox<Realmtimezone> comboRealmTimezone = new JComboBox<>();
        comboRealmTimezone.setRenderer(new ListTimezoneCellRenderer());
        
        for (Realmtimezone t : listTimezones) {
            comboRealmTimezone.addItem(t);
        }
        
        comboRealmTimezone.setSelectedItem(this.selectedTimezone);
        comboRealmTimezone.addActionListener(this);        
        comboRealmTimezone.addFocusListener(this);
        
        if (isSelected) {
            comboRealmTimezone.setBackground(table.getSelectionBackground());
        } else {
            comboRealmTimezone.setBackground(table.getSelectionForeground());
        }

        return comboRealmTimezone;
    }        
    
    @Override
    public void actionPerformed(ActionEvent e) {        
        JComboBox<Realmtimezone> comboRealmTimezone = (JComboBox<Realmtimezone>) e.getSource();        
        this.selectedTimezone = (Realmtimezone) comboRealmTimezone.getSelectedItem();                
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
