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
import eu.jangos.manager.controller.ParameterService;
import eu.jangos.manager.model.Account;
import eu.jangos.manager.model.Bannedaccount;
import eu.jangos.manager.model.Locale;
import eu.jangos.manager.model.Realm;
import eu.jangos.manager.utils.ParameterConstants;
import eu.jangos.manager.utils.Utils;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("eu/jangos/manager/Bundle"); // NOI18N
    private final String[] COLUMN_NAME = {bundle.getString("JTableAccountModel.name"),
        bundle.getString("JTableAccountModel.password"), 
        bundle.getString("JTableAccountModel.email"), 
        bundle.getString("JTableAccountModel.banned"), 
        bundle.getString("JTableAccountModel.unban"), 
        bundle.getString("JTableAccountModel.reason"),  
        bundle.getString("JTableAccountModel.attempt"), 
        bundle.getString("JTableAccountModel.locked"), 
        bundle.getString("JTableAccountModel.locale"), 
        bundle.getString("JTableAccountModel.online"), 
        bundle.getString("JTableAccountModel.realm"), 
        bundle.getString("JTableAccountModel.lastlogin"),             
        bundle.getString("JTableAccountModel.lastip"), 
        bundle.getString("JTableAccountModel.creation")};
    private final int COLUMN_COUNT = COLUMN_NAME.length;
    private final Class[] COLUMN_CLASS = {String.class, String.class, String.class, Boolean.class, String.class, String.class, Integer.class, Boolean.class, Locale.class, Boolean.class, Realm.class, String.class, String.class, String.class};

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private AccountService as;
    private ParameterService ps;
    
    /**
     * listAccounts is the list of accounts for this table model.
     */
    private List<Account> listAccounts;
    private List<Account> listEditedAccounts;
    private final List<Account> listAddedAccounts;

    public JTableAccountModel() {
        this.listAccounts = new ArrayList<>();
        this.listEditedAccounts = new ArrayList<>();
        this.listAddedAccounts = new ArrayList<>();
        as = null;
        ps = null;
    }

    public JTableAccountModel(AccountService accountService, ParameterService parameterService) {
        this.listAccounts = new ArrayList<>();
        this.listEditedAccounts = new ArrayList<>();
        this.listAddedAccounts = new ArrayList<>();
        as = accountService;
        ps = parameterService;
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
            case "Password":
                return this.listAccounts.get(rowIndex).getHashPass();
            case "Email":
                return this.listAccounts.get(rowIndex).getEmail();
            case "Locale":
                return this.listAccounts.get(rowIndex).getLocale();
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
                String name = ((String) object).toUpperCase();
                if (!this.listAccounts.get(rowIndex).getName().equals(name)) {
                    this.listAccounts.get(rowIndex).setName(name);
                    if(!this.listEditedAccounts.contains(this.listAccounts.get(rowIndex)))
                    {
                        this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                    }
                }
                break;
            case "Password": 
                // We set the hashpass to the corresponding value to not update it in case of failure with MessageDisgest.
                String hashpass = this.listAccounts.get(rowIndex).getHashPass();
                try {
                    hashpass = Utils.createHashPass(this.listAccounts.get(rowIndex).getName(), (String) object);
                } catch (NoSuchAlgorithmException ex) {                    
                }            
                if (!this.listAccounts.get(rowIndex).getHashPass().equals(hashpass)) {
                    this.listAccounts.get(rowIndex).setHashPass(hashpass);
                    // Resetting the verifier and the salt
                    // Will be recalculated by the authentication server.
                    this.listAccounts.get(rowIndex).setVerifier(null);
                    this.listAccounts.get(rowIndex).setSalt(null);
                    this.listAccounts.get(rowIndex).setSessionkey(null);
                    if(!this.listEditedAccounts.contains(this.listAccounts.get(rowIndex)))
                    {
                        this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                    }
                }
            break;
            case "Email":
                if (!this.listAccounts.get(rowIndex).getEmail().equals((String) object)) {
                    this.listAccounts.get(rowIndex).setEmail((String) object);
                    if(!this.listEditedAccounts.contains(this.listAccounts.get(rowIndex)))
                    {
                        this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                    }
                }
                break;
            case "Locale":
                if(!this.listAccounts.get(rowIndex).getLocale().equals((Locale) object)) {                                        
                    this.listAccounts.get(rowIndex).setLocale((Locale) object);
                    if(!this.listEditedAccounts.contains(this.listAccounts.get(rowIndex)))
                    {
                        this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                    }
                }                                
                break;
            case "Realm":                                
                if((this.listAccounts.get(rowIndex).getRealm() != null
                        && !this.listAccounts.get(rowIndex).getRealm().equals((Realm) object)) 
                        || (this.listAccounts.get(rowIndex).getRealm() == null && object != null)) {
                    if(((Realm) object).getId() == null)
                    {
                        this.listAccounts.get(rowIndex).setRealm(null);
                    } else {
                        this.listAccounts.get(rowIndex).setRealm((Realm) object);
                    }
                    if(!this.listEditedAccounts.contains(this.listAccounts.get(rowIndex)))
                    {
                        this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                    }
                }
                break;
            case "Attempt":
                if (this.listAccounts.get(rowIndex).getFailedattempt() != (int) object) {
                    this.listAccounts.get(rowIndex).setFailedattempt((int) object);
                    if(this.listAccounts.get(rowIndex).getFailedattempt() >= Integer.parseInt(ps.getParameter(ParameterConstants.KEY_MAX_FAILED_ATTEMPT)))
                    {
                        this.listAccounts.get(rowIndex).setLocked(true);
                    } else {
                        this.listAccounts.get(rowIndex).setLocked(false);
                    }
                    if(!this.listEditedAccounts.contains(this.listAccounts.get(rowIndex)))
                    {
                        this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                    }
                }
                break;
            case "Online":
                if (this.listAccounts.get(rowIndex).isOnline() != (boolean) object) {
                    this.listAccounts.get(rowIndex).setOnline((boolean) object);
                    if(!this.listEditedAccounts.contains(this.listAccounts.get(rowIndex)))
                    {
                        this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                    }
                }
                break;
            case "Locked":
                if (this.listAccounts.get(rowIndex).isLocked() != (boolean) object) {
                    this.listAccounts.get(rowIndex).setLocked((boolean) object);
                    if(!this.listEditedAccounts.contains(this.listAccounts.get(rowIndex)))
                    {
                        this.listEditedAccounts.add(this.listAccounts.get(rowIndex));
                    }
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

        switch (COLUMN_NAME[columnIndex]) {
            case "Name":
                return this.listAddedAccounts.contains(this.listAccounts.get(rowIndex));                                                    
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
     * Returns a column given its name. Implementation is naive so this should
     * be overridden if this method is to be called often. This method is not in
     * the <code>TableModel</code> interface and is not used by the
     * <code>JTable</code>.
     *
     * @param columnName string containing name of column to be located
     * @return the column with <code>columnName</code>, or -1 if not found
     */
    public int findColumn(String columnName) {
        for (int i = 0; i < COLUMN_COUNT; i++) {
            if (COLUMN_NAME[i].equals(columnName)) {
                return i;
            }
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

        this.listAddedAccounts.remove(this.listAccounts.get(idx));
        this.listEditedAccounts.remove(this.listAccounts.get(idx));
        this.listAccounts.remove(idx);
        fireTableRowsDeleted(idx, idx);
    }

    /**
     * Add a new row into the model.
     * @param account The account to be added in the backing model.
     */
    public void addRow(Account account) {
        this.listAccounts.add(account);
        this.listAddedAccounts.add(account);
        fireTableRowsInserted(this.listAccounts.size(), this.listAccounts.size());
    }
    
    /**
     * Checks whether this account is a newly added one or an edited one.
     * @param account The account to check.
     * @return true if this is a new row, false otherwise.
     */
    public boolean isNewRow(Account account) {
        return this.listAddedAccounts.contains(account);
    }
    
    /**
     * Merge the account with an existing row of the backing list.
     * @param account The account to be merged.
     */
    public void mergeRow(Account account) {
        boolean found = false;
        for(int i = 0; i < this.listAccounts.size(); i++)
        {
            if(this.listAccounts.get(i).getName().equals(account.getName()))
            {
                this.listAccounts.set(i, account);
                found = true;
                fireTableRowsUpdated(i, i);
                break;
            }
        }
        
        if(!found)
        {
            this.listAccounts.add(account);
            fireTableRowsInserted(this.listAccounts.size(), this.listAccounts.size());
        }
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

    /**
     * Remove the account from the added account list.
     * @param account The account to be removed from the list.
     */
    public void removeAddedAccount(Account account) {
        this.listAddedAccounts.remove(account);
    }
    
    /**
     * Remove the account from the edited account list.
     * @param account The account to be removed from the list.
     */
    public void removeEditedAccount(Account account) {
        this.listEditedAccounts.remove(account);
    }

    public AccountService getAs() {
        return this.as;
    }

    public void setAs(AccountService as) {
        this.as = as;
    }

    public ParameterService getPs() {
        return this.ps;
    }

    public void setPs(ParameterService ps) {
        this.ps = ps;
    }
}
