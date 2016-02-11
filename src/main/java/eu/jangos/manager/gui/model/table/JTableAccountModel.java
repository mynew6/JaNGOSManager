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
package eu.jangos.manager.gui.model.table;

import eu.jangos.manager.controller.AccountService;
import eu.jangos.manager.model.Account;
import eu.jangos.manager.model.Bannedaccount;
import eu.jangos.manager.model.Realm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * JTableAccountModel is an extension of an AbstractTableModel to manage the
 * accounts.
 *
 * @author Warkdev
 * @version v0.1
 * @since 11-02-2016
 */
public class JTableAccountModel extends AbstractTableModel {
    
    private static final String[] COLUMN_NAME = {"Name", "Email", "Banned", "Unban date", "Ban reason", "Attempt", "Locked", "Locale", "Online", "Realm", "Last login", "Last IP", "Creation"};
    private static final int COLUMN_COUNT = COLUMN_NAME.length;
    private static final Class[] COLUMN_CLASS = {String.class, String.class, Boolean.class, String.class, String.class, Integer.class, Boolean.class, String.class, Boolean.class, Realm.class, String.class, String.class, String.class};    
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    private static AccountService as;
    /**
     * listAccounts is the list of accounts for this table model.
     */
    private List<Account> listAccounts;
    private List<Account> listEditedAccounts;    

    public JTableAccountModel() {
        this.listAccounts = new ArrayList<>();
        this.listEditedAccounts = new ArrayList<>();                
        as = new AccountService();
    }

    public JTableAccountModel(AccountService accountService) {
        this.listAccounts = new ArrayList<>();
        this.listEditedAccounts = new ArrayList<>();        
        as = accountService;
    }

    @Override
    public int getRowCount() {
        return this.listAccounts.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > this.listAccounts.size()) {
            return null;
        }

        if (columnIndex < 0 || columnIndex > COLUMN_COUNT) {
            return null;
        }

        Bannedaccount banInfo = as.getBanInfo(this.listAccounts.get(rowIndex));
        
        switch (COLUMN_NAME[columnIndex]) {
            case "Name":
                return this.listAccounts.get(rowIndex).getName();
            case "Email":
                return this.listAccounts.get(rowIndex).getEmail();
            case "Locale":
                return this.listAccounts.get(rowIndex).getLocale().getLocale();
            case "Realm":
                return this.listAccounts.get(rowIndex).getRealm();
            case "Last IP":
                return this.listAccounts.get(rowIndex).getLastIp();
            case "Attempt":
                return this.listAccounts.get(rowIndex).getFailedattempt();
            case "Banned":
                return (banInfo != null);
            case "Unban date":
                return (banInfo == null ? "" : sdf.format(banInfo.getUnban()));
            case "Ban reason":
                return (banInfo == null ? "" : banInfo.getReason());
            case "Online":
                return this.listAccounts.get(rowIndex).isOnline();
            case "Locked":
                return this.listAccounts.get(rowIndex).isLocked();
            case "Last login":
                return sdf.format(this.listAccounts.get(rowIndex).getLastlogin());
            case "Creation":
                return sdf.format(this.listAccounts.get(rowIndex).getCreation());
        }

        return null;
    }

    @Override
    public void setValueAt(Object object, int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > this.listAccounts.size()) {
            return;
        }

        if (columnIndex < 0 || columnIndex > COLUMN_COUNT) {
            return;
        }       
        
        switch (COLUMN_NAME[columnIndex]) {
            case "Name":
                if(!this.listAccounts.get(rowIndex).getName().equals((String) object))
                {
                    this.listAccounts.get(rowIndex).setName((String) object);
                    this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                }
                break;
            case "Email":
                if(!this.listAccounts.get(rowIndex).getEmail().equals((String) object))
                {
                    this.listAccounts.get(rowIndex).setEmail((String) object);
                    this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                }                
                break;
            case "Locale":
                //this.listAccounts.get(rowIndex).setEmail((String) object);
                break;
            case "Realm":
                this.listAccounts.get(rowIndex).setRealm((Realm) object);
                break;            
            case "Attempt":
                if(this.listAccounts.get(rowIndex).getFailedattempt() != (int) object)
                {
                    this.listAccounts.get(rowIndex).setFailedattempt((int) object);
                    this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                }
                break;            
            case "Online":
                if(this.listAccounts.get(rowIndex).isOnline() != (boolean) object)
                {
                    this.listAccounts.get(rowIndex).setOnline((boolean) object);
                    this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                }                
                break;
            case "Locked":
                if(this.listAccounts.get(rowIndex).isLocked() != (boolean) object)
                {
                    this.listAccounts.get(rowIndex).setLocked((boolean) object);
                    this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                }                
                break;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > this.listAccounts.size()) {
            return false;
        }

        if (columnIndex < 0 || columnIndex > COLUMN_COUNT) {
            return false;
        }

        switch(COLUMN_NAME[columnIndex])
        {
            case "Last IP":
            case "Banned":
            case "Unban date":                
            case "Ban reason":                
            case "Last login":                
            case "Creation":
                return false;
        }
        
        return true;
    }

    /**
     * Return the column name corresponding to the given ID.
     *
     * @param id The ID of the column.
     * @return The column name, null if the id is not available in the class
     * definition.
     */
    @Override
    public String getColumnName(int id) {
        if (id < 0 || id > COLUMN_COUNT) {
            return null;
        }

        return COLUMN_NAME[id];
    }

    /**
     * Return the type of the column.
     *
     * @param columnIndex The index of the column for which the type is
     * requested.
     * @return The column class, null if the index is not valid.
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex < 0 || columnIndex > COLUMN_COUNT) {
            return null;
        }

        return COLUMN_CLASS[columnIndex];
    }            

    @Override
    /**
     * Returns a column given its name.
     * Implementation is naive so this should be overridden if
     * this method is to be called often. This method is not
     * in the <code>TableModel</code> interface and is not used by the
     * <code>JTable</code>.
     *
     * @param columnName string containing name of column to be located
     * @return the column with <code>columnName</code>, or -1 if not found
     */
    public int findColumn(String columnName) {   
        for(int i = 0; i < COLUMN_COUNT; i++)
        {
            if(COLUMN_NAME[i].equals(columnName))
                return i;
        }
        
        return -1;
    }        
    
    /**
     * Remove the row at the given index.
     *
     * @param idx The index of the row to be removed.
     */
    public void removeRow(int idx) {
        if (idx < 0 || idx > this.listAccounts.size()) {
            return;
        }

        this.listAccounts.remove(idx);
        fireTableRowsDeleted(idx, idx);
    }

    /**
     * This method erase the content of the TableAccountMode.
     */
    public void erase() {
        this.listAccounts = new ArrayList<>();
        fireTableDataChanged();
    }

    public List<Account> getListAccounts() {
        return this.listAccounts;
    }

    public void setListAccounts(List<Account> listAccounts) {
        if (listAccounts == null) {
            return;
        }
        this.listAccounts = listAccounts;
        fireTableDataChanged();
    }

    public List<Account> getListEditedAccounts() {
        return listEditedAccounts;
    }

    public void setListEditedAccounts(List<Account> listEditedAccounts) {
        this.listEditedAccounts = listEditedAccounts;
    }
       
    /**
     * Return the account with the given ID index.
     *
     * @param id The index of the account in the backing list.
     * @return The account corresponding to the given index, null if the index
     * is invalid.
     */
    public Account getAccount(int id) {
        if (id < 0 || id > this.listAccounts.size()) {
            return null;
        }

        return this.listAccounts.get(id);
    }

}
