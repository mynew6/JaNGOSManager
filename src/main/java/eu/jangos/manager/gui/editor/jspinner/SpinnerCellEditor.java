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
package eu.jangos.manager.gui.editor.jspinner;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Warkdev
 * @version 1.0
 * @since 11-02-2016
 */
public class SpinnerCellEditor extends AbstractCellEditor 
        implements TableCellEditor, ActionListener {

    private JSpinner spinner;    

    public SpinnerCellEditor(int max) {
        this.spinner = new JSpinner();
        this.spinner.setModel(new SpinnerNumberModel(0, 0, max, 1));        
    }        
    
    @Override
    public Object getCellEditorValue() {
        return this.spinner.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.spinner.setValue(value);
        return this.spinner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No interest.
    }
}
