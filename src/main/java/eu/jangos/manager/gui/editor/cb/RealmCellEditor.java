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

import eu.jangos.manager.gui.renderer.cb.ListRealmCellRenderer;
import eu.jangos.manager.model.Realm;
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
 * @since 11-02-2016
 */
public class RealmCellEditor extends AbstractCellEditor 
        implements TableCellEditor, ActionListener, FocusListener {

    private final List<Realm> listRealms;
    private Realm selectedRealm;

    public RealmCellEditor(List<Realm> listRealms) {
        this.listRealms = listRealms;
        this.selectedRealm = listRealms.get(0);        
    }        
    
    @Override
    public Object getCellEditorValue() {        
        return this.selectedRealm;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {                   
        if(value instanceof Realm) {
            this.selectedRealm = (Realm) value;
        }                                
        
        JComboBox<Realm> comboRealm = new JComboBox<>();
        comboRealm.setRenderer(new ListRealmCellRenderer());
        
        for (Realm r : listRealms) {
            comboRealm.addItem(r);
        }
        
        comboRealm.setSelectedItem(this.selectedRealm);
        comboRealm.addActionListener(this);        
        comboRealm.addFocusListener(this);
        
        if (isSelected) {
            comboRealm.setBackground(table.getSelectionBackground());
        } else {
            comboRealm.setBackground(table.getSelectionForeground());
        }

        return comboRealm;
    }        
    
    @Override
    public void actionPerformed(ActionEvent e) {        
        JComboBox<Realm> comboRealm = (JComboBox<Realm>) e.getSource();        
        this.selectedRealm = (Realm) comboRealm.getSelectedItem();                
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
