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

import eu.jangos.manager.model.Realm;
import eu.jangos.manager.model.Realmtimezone;
import eu.jangos.manager.model.Realmtype;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * JTableRealmModel is an extension of an AbstractTableModel to manage the
 * realms.
 *
 * @author Warkdev
 * @version v0.1
 * @since 11-02-2016
 */
public class JTableRealmModel extends AbstractTableModel {

    private final String[] COLUMN_NAME = {"Name", "Address", "Port", "Type", "Timezone", "Population", "Max. Players", "Total Players", "Invalid", "Offline", "Show version", "New Players", "Recommended"};
    private final int COLUMN_COUNT = COLUMN_NAME.length;
    private final Class[] COLUMN_CLASS = {String.class, String.class, Integer.class, Realmtype.class, Realmtimezone.class, String.class, Integer.class, Integer.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class};

    /**
     * listRealms is the list of realms for this table model.
     */
    private List<Realm> listRealms;
    private List<Realm> listEditedRealms;
    private final List<Realm> listAddedRealms;

    private double average;
    
    public JTableRealmModel() {
        this.listRealms = new ArrayList<>();
        this.listEditedRealms = new ArrayList<>();
        this.listAddedRealms = new ArrayList<>();
        this.average = 0;
    }

    @Override
    public int getRowCount() {
        return this.listRealms.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > this.listRealms.size()) {
            return null;
        }

        if (columnIndex < 0 || columnIndex > COLUMN_COUNT) {
            return null;
        }

        switch (COLUMN_NAME[columnIndex]) {
            case "Name":
                return this.listRealms.get(rowIndex).getName();
            case "Address":
                return this.listRealms.get(rowIndex).getAddress();
            case "Port":
                return this.listRealms.get(rowIndex).getPort();
            case "Type":
                return this.listRealms.get(rowIndex).getRealmtype();
            case "Timezone":
                return this.listRealms.get(rowIndex).getRealmtimezone();
            case "Population":                
                double low = this.average - Math.floor(average * 5f) / 10f;
                double high = this.average + Math.floor(average * 5f) / 10f;
                double population = Math.floor(this.listRealms.get(rowIndex).getPopulation() * 10f) / 10f;
                      
                // This is how the Blizzard clients handle it:
                // population = value sent by the server (count players / maximum players).
                // average = the average of all the "population" fields in the database.
                // low limit = average - floor(average / 2)
                // high limit = average + floor(average / 2)
                // All the population lower or equals to low are "LOW"
                // All the population between low and high limits are "MEDIUM"
                // All the population higher or equals to high are "HIGH"
                if(population <= low)
                {
                    return "LOW";
                }
            
                if(low <= population && population <= high)
                {
                    return "MEDIUM";
                }
                
                return "HIGH";
            case "Max. Players":
                return this.listRealms.get(rowIndex).getMaxPlayers();
            case "Total Players":
                return this.listRealms.get(rowIndex).getCountPlayers();
            case "Invalid":
                return this.listRealms.get(rowIndex).isInvalid();
            case "Offline":
                return this.listRealms.get(rowIndex).isOffline();
            case "Show version":
                return this.listRealms.get(rowIndex).isShowversion();
            case "New Players":
                return this.listRealms.get(rowIndex).isNewplayers();
            case "Recommended":
                return this.listRealms.get(rowIndex).isRecommended();
        }

        return null;
    }

    @Override
    public void setValueAt(Object object, int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > this.listRealms.size()) {
            return;
        }

        if (columnIndex < 0 || columnIndex > COLUMN_COUNT) {
            return;
        }

        switch (COLUMN_NAME[columnIndex]) {
            case "Name":
                String name = ((String) object);
                if (!this.listRealms.get(rowIndex).getName().equals(name)) {
                    this.listRealms.get(rowIndex).setName(name);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Address":
                if (!this.listRealms.get(rowIndex).getAddress().equals((String) object)) {
                    this.listRealms.get(rowIndex).setAddress((String) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Port":
                if (this.listRealms.get(rowIndex).getPort() != (int) object) {
                    this.listRealms.get(rowIndex).setPort((int) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Type":                
                if ((this.listRealms.get(rowIndex).getRealmtype()==null && object != null)
                        || !this.listRealms.get(rowIndex).getRealmtype().equals((Realmtype) object)) {
                    this.listRealms.get(rowIndex).setRealmtype((Realmtype) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Timezone":                
                if ((this.listRealms.get(rowIndex).getRealmtimezone()==null && object != null)
                        || !this.listRealms.get(rowIndex).getRealmtimezone().equals((Realmtimezone) object)) {
                    this.listRealms.get(rowIndex).setRealmtimezone((Realmtimezone) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Population":
                if (this.listRealms.get(rowIndex).getPopulation() != (float) object) {
                    this.listRealms.get(rowIndex).setPopulation((float) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Max. Players":
                if (this.listRealms.get(rowIndex).getMaxPlayers() != (int) object) {
                    this.listRealms.get(rowIndex).setMaxPlayers((int) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Total Players":
                if (this.listRealms.get(rowIndex).getCountPlayers() != (int) object) {
                    this.listRealms.get(rowIndex).setCountPlayers((int) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Invalid":
                if (this.listRealms.get(rowIndex).isInvalid() != (boolean) object) {
                    this.listRealms.get(rowIndex).setInvalid((boolean) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Offline":
                if (this.listRealms.get(rowIndex).isOffline()!= (boolean) object) {
                    this.listRealms.get(rowIndex).setOffline((boolean) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Show version":
                if (this.listRealms.get(rowIndex).isShowversion()!= (boolean) object) {
                    this.listRealms.get(rowIndex).setShowversion((boolean) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "New Players":
                if (this.listRealms.get(rowIndex).isNewplayers()!= (boolean) object) {
                    this.listRealms.get(rowIndex).setNewplayers((boolean) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
            case "Recommended":
                if (this.listRealms.get(rowIndex).isRecommended()!= (boolean) object) {
                    this.listRealms.get(rowIndex).setRecommended((boolean) object);
                    if (!this.listEditedRealms.contains(this.listRealms.get(rowIndex))) {
                        this.listEditedRealms.add(this.listRealms.get(rowIndex));
                    }
                }
                break;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > this.listRealms.size()) {
            return false;
        }

        if (columnIndex < 0 || columnIndex > COLUMN_COUNT) {
            return false;
        }

        switch (COLUMN_NAME[columnIndex]) {
            case "Population":                
            case "Total Players":
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
        if (idx < 0 || idx > this.listRealms.size()) {
            return;
        }

        this.listAddedRealms.remove(this.listRealms.get(idx));
        this.listEditedRealms.remove(this.listRealms.get(idx));
        this.listRealms.remove(idx);
        fireTableRowsDeleted(idx, idx);
    }

    /**
     * Add a new row into the model.
     *
     * @param realm The realm to be added in the backing model.
     */
    public void addRow(Realm realm) {
        this.listRealms.add(realm);
        this.listAddedRealms.add(realm);
        fireTableRowsInserted(this.listRealms.size(), this.listRealms.size());
    }

    /**
     * Checks whether this realm is a newly added one or an edited one.
     *
     * @param realm The account to check.
     * @return true if this is a new row, false otherwise.
     */
    public boolean isNewRow(Realm realm) {
        return this.listAddedRealms.contains(realm);
    }

    /**
     * Merge the realm with an existing row of the backing list.
     *
     * @param realm The account to be merged.
     */
    public void mergeRow(Realm realm) {
        boolean found = false;
        for (int i = 0; i < this.listRealms.size(); i++) {
            if (this.listRealms.get(i).getName().equals(realm.getName())) {
                this.listRealms.set(i, realm);
                found = true;
                fireTableRowsUpdated(i, i);
                break;
            }
        }

        if (!found) {
            this.listRealms.add(realm);
            fireTableRowsInserted(this.listRealms.size(), this.listRealms.size());
        }
    }

    /**
     * This method erase the content of the TableRealmModel.
     */
    public void erase() {
        this.listRealms = new ArrayList<>();
        fireTableDataChanged();
    }

    public List<Realm> getListRealms() {
        return this.listRealms;
    }

    public void setListRealms(List<Realm> listRealms) {
        if (listRealms == null) {
            return;
        }
        this.listRealms = listRealms;
        fireTableDataChanged();
    }

    public List<Realm> getListEditedRealms() {
        return listEditedRealms;
    }

    public void setListEditedRealms(List<Realm> listEditedRealms) {
        this.listEditedRealms = listEditedRealms;
    }

    /**
     * Return the realm with the given ID index.
     *
     * @param id The index of the account in the backing list.
     * @return The realm corresponding to the given index, null if the index
     * is invalid.
     */
    public Realm getRealm(int id) {
        if (id < 0 || id > this.listRealms.size()) {
            return null;
        }

        return this.listRealms.get(id);
    }

    /**
     * Remove the account from the added realm list.
     *
     * @param realm The account to be removed from the list.
     */
    public void removeAddedRealm(Realm realm) {
        this.listAddedRealms.remove(realm);
    }

    /**
     * Remove the realm from the edited realm list.
     *
     * @param realm The account to be removed from the list.
     */
    public void removeEditedAccount(Realm realm) {
        this.listEditedRealms.remove(realm);
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        // We round or floor depending on the comma. This is because MySQL database can actually calculate
        // that 1 / 2 = 0.49 or 0.51.
        if((average * 10f) % 1 >= 0.5)
        {
            this.average = Math.round(average * 10f) / 10f;
        } else {
            this.average = Math.floor(average * 10f) / 10f;
        }        
    }        
}
