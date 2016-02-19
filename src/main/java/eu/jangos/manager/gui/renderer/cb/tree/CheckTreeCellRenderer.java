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
package eu.jangos.manager.gui.renderer.cb.tree;

import eu.jangos.manager.gui.model.tree.CheckTreeSelectionModel;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 *
 * @author Warkdev
 */
public class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer { 
    private CheckTreeSelectionModel selectionModel; 
    private TreeCellRenderer delegate; 
    private TristateCheckBox checkBox = new TristateCheckBox(); 
 
    public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel){ 
        this.delegate = delegate; 
        this.selectionModel = selectionModel; 
        setLayout(new BorderLayout()); 
        setOpaque(false); 
        checkBox.setOpaque(false); 
    } 
 
 
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){ 
        Component renderer = delegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus); 
 
        TreePath path = tree.getPathForRow(row); 
        if(path!=null){ 
            if(selectionModel.isPathSelected(path, true)) 
                checkBox.setState(TristateCheckBox.SELECTED); 
            else 
                checkBox.setState(selectionModel.isPartiallySelected(path) ? TristateCheckBox.DONT_CARE : TristateCheckBox.NOT_SELECTED); 
        } 
        removeAll(); 
        add(checkBox, BorderLayout.WEST); 
        add(renderer, BorderLayout.CENTER); 
        return this; 
    } 
} 
