package eu.jangos.manager.gui.frame;

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


import eu.jangos.manager.controller.AccountService;
import eu.jangos.manager.controller.filters.BooleanType;
import eu.jangos.manager.controller.filters.DateType;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FrameManageAccount is the screen that will handle all the accounts related activities.
 * @author Warkdev
 * @version v0.1
 */
public class FrameManageAccount extends javax.swing.JInternalFrame {
    private static final Logger logger = LoggerFactory.getLogger(FrameManageAccount.class);
    
    private final AccountService as;
    
    /**
     * Creates new form FrameManageAccount
     */
    public FrameManageAccount() {
        initComponents();
        // Sort this table by name per default.
        this.jTableAccounts.getRowSorter().toggleSortOrder(0);
        
        this.jTableAccountsModel = (DefaultTableModel) this.jTableAccounts.getModel();     
        this.as = new AccountService();        
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupLocked = new javax.swing.ButtonGroup();
        jScrollPaneTableAccounts = new javax.swing.JScrollPane();
        jTableAccounts = new javax.swing.JTable();
        jPanelControls = new javax.swing.JPanel();
        jLabelName = new javax.swing.JLabel();
        jTFName = new javax.swing.JTextField();
        jCBMatch = new javax.swing.JCheckBox();
        jRadioButtonLocked = new javax.swing.JRadioButton();
        jRadioButtonUnlocked = new javax.swing.JRadioButton();
        jRadioButtonLockBoth = new javax.swing.JRadioButton();
        jButtonSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jTableAccounts.setAutoCreateRowSorter(true);
        jTableAccounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Email", "Creation", "Locked", "Banned"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneTableAccounts.setViewportView(jTableAccounts);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 554;
        gridBagConstraints.ipady = 358;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPaneTableAccounts, gridBagConstraints);

        jPanelControls.setBorder(javax.swing.BorderFactory.createTitledBorder("Controls"));
        jPanelControls.setLayout(new java.awt.GridBagLayout());

        jLabelName.setText("Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanelControls.add(jLabelName, gridBagConstraints);

        jTFName.setMinimumSize(new java.awt.Dimension(70, 20));
        jTFName.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanelControls.add(jTFName, gridBagConstraints);

        jCBMatch.setText("Match");
        jCBMatch.setToolTipText("Retrieve only account matching the name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanelControls.add(jCBMatch, gridBagConstraints);

        buttonGroupLocked.add(jRadioButtonLocked);
        jRadioButtonLocked.setText("Locked");
        jRadioButtonLocked.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonLockedItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanelControls.add(jRadioButtonLocked, gridBagConstraints);

        buttonGroupLocked.add(jRadioButtonUnlocked);
        jRadioButtonUnlocked.setText("Unlocked");
        jRadioButtonUnlocked.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonUnlockedItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanelControls.add(jRadioButtonUnlocked, gridBagConstraints);

        buttonGroupLocked.add(jRadioButtonLockBoth);
        jRadioButtonLockBoth.setSelected(true);
        jRadioButtonLockBoth.setText("Both");
        jRadioButtonLockBoth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonLockBothItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanelControls.add(jRadioButtonLockBoth, gridBagConstraints);

        jButtonSearch.setText("Search");
        jButtonSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonSearchMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        jPanelControls.add(jButtonSearch, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(jPanelControls, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
    private void updateTable(AccountDTO account)
    {
        boolean ban = as.isAccountBanned(account.getId());
        this.jTableAccountsModel.addRow(new Object[]{account.getName(), account.getEmail(), account.getCreation(), account.getLocked(), ban});            
    }
    
    private void updateTable(List<AccountDTO> listAccounts)
    {        
        for (AccountDTO account : listAccounts) {
            updateTable(account);
        }
    }
    */
    private void jButtonSearchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSearchMouseReleased
        String search = this.jTFName.getText().replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s+", "+");        
        
        this.as.getAllAccounts("%", DateType.NONE, null, null, DateType.NONE, null, null, BooleanType.BOTH, BooleanType.BOTH, BooleanType.BOTH);
        
        this.jTableAccountsModel.setRowCount(0);              
        
        if(search.isEmpty())
        {            
            return;
        }                
        
        this.jTFName.setText(search);     
                    
    }//GEN-LAST:event_jButtonSearchMouseReleased

    private void jRadioButtonLockBothItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonLockBothItemStateChanged
       // if(evt.getStateChange() == ItemEvent.SELECTED)
        //    this.lockType = LockType.BOTH;
    }//GEN-LAST:event_jRadioButtonLockBothItemStateChanged

    private void jRadioButtonUnlockedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonUnlockedItemStateChanged
       // if(evt.getStateChange() == ItemEvent.SELECTED)
//            this.lockType = LockType.UNLOCKED;
    }//GEN-LAST:event_jRadioButtonUnlockedItemStateChanged

    private void jRadioButtonLockedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonLockedItemStateChanged
      //  if(evt.getStateChange() == ItemEvent.SELECTED)
     //       this.lockType = LockType.LOCKED;
    }//GEN-LAST:event_jRadioButtonLockedItemStateChanged
 
    private void showWarning(String title, String message)
    {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    private void showError(String title, String message)
    {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupLocked;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JCheckBox jCBMatch;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JPanel jPanelControls;
    private javax.swing.JRadioButton jRadioButtonLockBoth;
    private javax.swing.JRadioButton jRadioButtonLocked;
    private javax.swing.JRadioButton jRadioButtonUnlocked;
    private javax.swing.JScrollPane jScrollPaneTableAccounts;
    private javax.swing.JTextField jTFName;
    private javax.swing.JTable jTableAccounts;
    // End of variables declaration//GEN-END:variables
    private DefaultTableModel jTableAccountsModel;
}
