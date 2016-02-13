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
import eu.jangos.manager.controller.LocaleService;
import eu.jangos.manager.controller.ParameterService;
import eu.jangos.manager.controller.RealmService;
import eu.jangos.manager.controller.filters.BooleanType;
import eu.jangos.manager.controller.filters.DateType;
import eu.jangos.manager.gui.dialog.DialogBan;
import eu.jangos.manager.gui.editor.cb.LocaleCellEditor;
import eu.jangos.manager.gui.editor.cb.RealmCellEditor;
import eu.jangos.manager.gui.editor.jspinner.SpinnerCellEditor;
import eu.jangos.manager.gui.model.table.JTableAccountModel;
import eu.jangos.manager.gui.renderer.cb.ListLocaleCellRenderer;
import eu.jangos.manager.gui.renderer.cb.ListRealmCellRenderer;
import eu.jangos.manager.gui.renderer.cb.LocaleCellRenderer;
import eu.jangos.manager.gui.renderer.cb.RealmCellRenderer;
import eu.jangos.manager.model.Account;
import eu.jangos.manager.model.Realm;
import eu.jangos.manager.model.Locale;
import eu.jangos.manager.utils.ParameterConstants;
import eu.jangos.manager.utils.Utils;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FrameManageAccount is the screen that will handle all the accounts related
 * activities.
 *
 * @author Warkdev
 * @version v0.1
 */
public class FrameManageAccount extends javax.swing.JInternalFrame {

    private static final Logger logger = LoggerFactory.getLogger(FrameManageAccount.class);
    private static final String ICON_IMAGE = "/images/account.png";
    private static final int DEFAULT_BAN_DURATION = 60;

    private final AccountService as = new AccountService();
    private final RealmService rs = new RealmService();
    private final LocaleService ls = new LocaleService();
    private final ParameterService ps = new ParameterService();
    
    private final DialogBan dialogBan;
    private final JFrame parent;

    private SwingWorkerAccount worker;

    private BooleanType locked;
    private BooleanType banned;
    private BooleanType online;
    private DateType login;
    private DateType creation;

    /**
     * Creates new form FrameManageAccount
     *
     * @param parent The parent JFrame of this internal frame.
     */
    public FrameManageAccount(JFrame parent) {
        initComponents();

        this.parent = parent;
        this.dialogBan = new DialogBan(this.parent, true);

        // We set a default duration
        this.dialogBan.setDuration(DEFAULT_BAN_DURATION);
        this.dialogBan.setCode(JOptionPane.CANCEL_OPTION);

        // Sort this table by name per default.        
        this.setFrameIcon(Utils.createImageIcon(ICON_IMAGE, getClass()));

        this.jTableAccounts.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.worker = new SwingWorkerAccount();        
        
        this.jTableAccounts.setDefaultRenderer(Locale.class, new LocaleCellRenderer());
        this.jTableAccounts.setDefaultEditor(Locale.class, new LocaleCellEditor(this.ls.getAllLocale()));
        
        this.jTableAccounts.setRowHeight(25);

        List<Locale> listLocales = this.ls.getAllLocale();
        List<Realm> listRealms = this.rs.getAllRealms();
        
        // We add dummies values for editing purposes.
        listLocales.add(0, new Locale(-1, "ALL", "ALL"));
        listRealms.add(0, new Realm(null, null, "ALL", null, 0, 0, 0, 0, false, false, false, false, false));
        
        this.jCBLocale.setModel(new DefaultComboBoxModel(listLocales.toArray()));
        this.jCBLocale.setRenderer(new ListLocaleCellRenderer());        
        
        this.jCBRealm.setModel(new DefaultComboBoxModel(listRealms.toArray()));
        this.jCBRealm.setRenderer(new ListRealmCellRenderer());
        
        this.locked = BooleanType.BOTH;
        this.banned = BooleanType.BOTH;
        this.online = BooleanType.BOTH;
        this.login = DateType.NONE;
        this.creation = DateType.NONE;

        Date now = new Date();
        this.jDatePickerCreationFrom.setLocale(java.util.Locale.UK);
        this.jDatePickerCreationTo.setLocale(java.util.Locale.UK);
        this.jDatePickerLoginFrom.setLocale(java.util.Locale.UK);
        this.jDatePickerLoginTo.setLocale(java.util.Locale.UK);
        this.jDatePickerCreationFrom.setDate(now);
        this.jDatePickerCreationTo.setDate(now);
        this.jDatePickerLoginFrom.setDate(now);
        this.jDatePickerLoginTo.setDate(now);
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

        jScrollPaneTableAccounts = new javax.swing.JScrollPane();
        jTableAccounts = new javax.swing.JTable();
        jPanelFilters = new javax.swing.JPanel();
        jPanelGeneric = new javax.swing.JPanel();
        jLabelName = new javax.swing.JLabel();
        jTFName = new javax.swing.JTextField();
        jCBMatch = new javax.swing.JCheckBox();
        jLabelLock = new javax.swing.JLabel();
        jCBLock = new javax.swing.JComboBox();
        jLabelBan = new javax.swing.JLabel();
        jCBBan = new javax.swing.JComboBox();
        jLabelOnline = new javax.swing.JLabel();
        jCBOnline = new javax.swing.JComboBox();
        jLabelLocale = new javax.swing.JLabel();
        jCBLocale = new javax.swing.JComboBox();
        jLabelRealm = new javax.swing.JLabel();
        jCBRealm = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jButtonReset = new javax.swing.JButton();
        jButtonSearch = new javax.swing.JButton();
        jPanelCreation = new javax.swing.JPanel();
        jLabelCreationFilter = new javax.swing.JLabel();
        jCBCreationFilter = new javax.swing.JComboBox();
        jLabelCreationFrom = new javax.swing.JLabel();
        jDatePickerCreationFrom = new org.jdesktop.swingx.JXDatePicker();
        jLabelCreationTo = new javax.swing.JLabel();
        jDatePickerCreationTo = new org.jdesktop.swingx.JXDatePicker();
        jPanelLogin = new javax.swing.JPanel();
        jLabelLoginFilter = new javax.swing.JLabel();
        jCBLoginFilter = new javax.swing.JComboBox();
        jLabelLoginFrom = new javax.swing.JLabel();
        jDatePickerLoginFrom = new org.jdesktop.swingx.JXDatePicker();
        jLabelLoginTo = new javax.swing.JLabel();
        jDatePickerLoginTo = new org.jdesktop.swingx.JXDatePicker();
        jPanelActions = new javax.swing.JPanel();
        jButtonUnlock = new javax.swing.JButton();
        jButtonLock = new javax.swing.JButton();
        jButtonUnban = new javax.swing.JButton();
        jButtonBan = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jPanelControls = new javax.swing.JPanel();
        jButtonCreate = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Account Management");
        setMinimumSize(new java.awt.Dimension(720, 480));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jTableAccounts.setModel(this.jTableAccountsModel);
        jTableAccounts.getTableHeader().setReorderingAllowed(false);
        jScrollPaneTableAccounts.setViewportView(jTableAccounts);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 554;
        gridBagConstraints.ipady = 358;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPaneTableAccounts, gridBagConstraints);

        jPanelFilters.setBorder(javax.swing.BorderFactory.createTitledBorder("Filters"));
        jPanelFilters.setLayout(new java.awt.GridBagLayout());

        jPanelGeneric.setMaximumSize(new java.awt.Dimension(400, 140));
        jPanelGeneric.setMinimumSize(new java.awt.Dimension(400, 140));
        jPanelGeneric.setName(""); // NOI18N
        jPanelGeneric.setPreferredSize(new java.awt.Dimension(400, 140));
        jPanelGeneric.setLayout(new java.awt.GridBagLayout());

        jLabelName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelName.setText("Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanelGeneric.add(jLabelName, gridBagConstraints);

        jTFName.setMaximumSize(new java.awt.Dimension(110, 25));
        jTFName.setMinimumSize(new java.awt.Dimension(110, 25));
        jTFName.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanelGeneric.add(jTFName, gridBagConstraints);

        jCBMatch.setText("Match");
        jCBMatch.setToolTipText("Retrieve only account exactly matching the name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanelGeneric.add(jCBMatch, gridBagConstraints);

        jLabelLock.setText("Lock:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanelGeneric.add(jLabelLock, gridBagConstraints);

        jCBLock.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BOTH", "LOCKED", "UNLOCKED" }));
        jCBLock.setMaximumSize(new java.awt.Dimension(110, 25));
        jCBLock.setMinimumSize(new java.awt.Dimension(110, 25));
        jCBLock.setPreferredSize(new java.awt.Dimension(110, 25));
        jCBLock.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBLockItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanelGeneric.add(jCBLock, gridBagConstraints);

        jLabelBan.setText("Ban:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanelGeneric.add(jLabelBan, gridBagConstraints);

        jCBBan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BOTH", "BANNED", "NOT BANNED" }));
        jCBBan.setMaximumSize(new java.awt.Dimension(110, 25));
        jCBBan.setMinimumSize(new java.awt.Dimension(110, 25));
        jCBBan.setPreferredSize(new java.awt.Dimension(110, 25));
        jCBBan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBBanItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanelGeneric.add(jCBBan, gridBagConstraints);

        jLabelOnline.setText("Online:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanelGeneric.add(jLabelOnline, gridBagConstraints);

        jCBOnline.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BOTH", "ON", "OFF" }));
        jCBOnline.setMaximumSize(new java.awt.Dimension(110, 25));
        jCBOnline.setMinimumSize(new java.awt.Dimension(110, 25));
        jCBOnline.setPreferredSize(new java.awt.Dimension(110, 25));
        jCBOnline.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBOnlineItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanelGeneric.add(jCBOnline, gridBagConstraints);

        jLabelLocale.setText("Locale:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanelGeneric.add(jLabelLocale, gridBagConstraints);

        jCBLocale.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALL" }));
        jCBLocale.setMaximumSize(new java.awt.Dimension(110, 25));
        jCBLocale.setMinimumSize(new java.awt.Dimension(110, 25));
        jCBLocale.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanelGeneric.add(jCBLocale, gridBagConstraints);

        jLabelRealm.setText("Realm:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanelGeneric.add(jLabelRealm, gridBagConstraints);

        jCBRealm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALL" }));
        jCBRealm.setMaximumSize(new java.awt.Dimension(110, 25));
        jCBRealm.setMinimumSize(new java.awt.Dimension(110, 25));
        jCBRealm.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        jPanelGeneric.add(jCBRealm, gridBagConstraints);

        jButtonReset.setText("Reset");
        jButtonReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonResetMouseReleased(evt);
            }
        });
        jPanel1.add(jButtonReset);

        jButtonSearch.setText("Search");
        jButtonSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonSearchMouseReleased(evt);
            }
        });
        jPanel1.add(jButtonSearch);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelGeneric.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelFilters.add(jPanelGeneric, gridBagConstraints);

        jPanelCreation.setBorder(javax.swing.BorderFactory.createTitledBorder("Creation"));
        jPanelCreation.setMaximumSize(new java.awt.Dimension(380, 140));
        jPanelCreation.setMinimumSize(new java.awt.Dimension(380, 140));
        jPanelCreation.setPreferredSize(new java.awt.Dimension(380, 140));
        jPanelCreation.setLayout(new java.awt.GridBagLayout());

        jLabelCreationFilter.setText("Filter:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanelCreation.add(jLabelCreationFilter, gridBagConstraints);

        jCBCreationFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "BEFORE", "AFTER", "BETWEEN" }));
        jCBCreationFilter.setMinimumSize(new java.awt.Dimension(110, 20));
        jCBCreationFilter.setPreferredSize(new java.awt.Dimension(110, 20));
        jCBCreationFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBCreationFilterItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanelCreation.add(jCBCreationFilter, gridBagConstraints);

        jLabelCreationFrom.setText("From:");
        jLabelCreationFrom.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanelCreation.add(jLabelCreationFrom, gridBagConstraints);

        jDatePickerCreationFrom.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanelCreation.add(jDatePickerCreationFrom, gridBagConstraints);

        jLabelCreationTo.setText("To:");
        jLabelCreationTo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanelCreation.add(jLabelCreationTo, gridBagConstraints);

        jDatePickerCreationTo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanelCreation.add(jDatePickerCreationTo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanelFilters.add(jPanelCreation, gridBagConstraints);

        jPanelLogin.setBorder(javax.swing.BorderFactory.createTitledBorder("Login"));
        jPanelLogin.setMaximumSize(new java.awt.Dimension(380, 140));
        jPanelLogin.setMinimumSize(new java.awt.Dimension(380, 140));
        jPanelLogin.setPreferredSize(new java.awt.Dimension(380, 140));
        jPanelLogin.setLayout(new java.awt.GridBagLayout());

        jLabelLoginFilter.setText("Filter:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanelLogin.add(jLabelLoginFilter, gridBagConstraints);

        jCBLoginFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "BEFORE", "AFTER", "BETWEEN" }));
        jCBLoginFilter.setMinimumSize(new java.awt.Dimension(110, 20));
        jCBLoginFilter.setPreferredSize(new java.awt.Dimension(110, 20));
        jCBLoginFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBLoginFilterItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanelLogin.add(jCBLoginFilter, gridBagConstraints);

        jLabelLoginFrom.setText("From:");
        jLabelLoginFrom.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanelLogin.add(jLabelLoginFrom, gridBagConstraints);

        jDatePickerLoginFrom.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanelLogin.add(jDatePickerLoginFrom, gridBagConstraints);

        jLabelLoginTo.setText("To:");
        jLabelLoginTo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        jPanelLogin.add(jLabelLoginTo, gridBagConstraints);

        jDatePickerLoginTo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        jPanelLogin.add(jDatePickerLoginTo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanelFilters.add(jPanelLogin, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(jPanelFilters, gridBagConstraints);

        jPanelActions.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));

        jButtonUnlock.setText("Unlock");
        jButtonUnlock.setToolTipText("Unlock all the selected accounts");
        jButtonUnlock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnlockActionPerformed(evt);
            }
        });
        jPanelActions.add(jButtonUnlock);

        jButtonLock.setText("Lock");
        jButtonLock.setToolTipText("Lock all the selected accounts");
        jButtonLock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLockActionPerformed(evt);
            }
        });
        jPanelActions.add(jButtonLock);

        jButtonUnban.setText("Unban");
        jButtonUnban.setToolTipText("Unban all the selected accounts");
        jButtonUnban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnbanActionPerformed(evt);
            }
        });
        jPanelActions.add(jButtonUnban);

        jButtonBan.setText("Ban");
        jButtonBan.setToolTipText("Ban all the selected accounts");
        jButtonBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBanActionPerformed(evt);
            }
        });
        jPanelActions.add(jButtonBan);

        jButtonDelete.setText("Delete");
        jButtonDelete.setToolTipText("Delete all the selected account");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        jPanelActions.add(jButtonDelete);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanelActions, gridBagConstraints);

        jPanelControls.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controls", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jButtonCreate.setText("Create");
        jButtonCreate.setToolTipText("Open the window to create a new account");
        jButtonCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateActionPerformed(evt);
            }
        });
        jPanelControls.add(jButtonCreate);

        jButtonSave.setText("Save");
        jButtonSave.setToolTipText("Save all the changes");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        jPanelControls.add(jButtonSave);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanelControls, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSearchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSearchMouseReleased
        if (this.worker.getState() == StateValue.STARTED) {
            this.worker.cancel(true);
        }
        this.worker = new SwingWorkerAccount();

        String search = this.jTFName.getText().replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s+", "+");

        this.jTableAccountsModel.erase();

        this.jTFName.setText(search);

        if (!this.jCBMatch.isSelected()) {
            search += "%";
        }

        if (search.isEmpty()) {
            return;
        }

        // Update Realm combobox.
        Realm selected = (Realm) this.jCBRealm.getSelectedItem();
        List<Realm> listRealms = this.rs.getAllRealms();
        listRealms.add(0, new Realm(null, null, "ALL", null, 0, 0, 0, 0, false, false, false, false, false));                        
        this.jCBRealm.setModel(new DefaultComboBoxModel(listRealms.toArray()));
        this.jCBRealm.setRenderer(new ListRealmCellRenderer());
        this.jCBRealm.setSelectedItem(selected);
        
        // Update of the cell renderer on search.
        this.jTableAccounts.setDefaultRenderer(Realm.class, new RealmCellRenderer());
        
        // Add dummy <none> value, replaced by null in business layer.
        List<Realm> listRealmTable = this.rs.getAllRealms();
        Realm r = new Realm(null, null, "<none>", null, 0, 0, 0, 0, false, false, false, false, false);
        r.setId(null);
        listRealmTable.add(0, r);
        this.jTableAccounts.setDefaultEditor(Realm.class, new RealmCellEditor(listRealmTable));
        this.jTableAccounts.setDefaultEditor(Integer.class, new SpinnerCellEditor(Integer.parseInt(this.ps.getParameter(ParameterConstants.KEY_MAX_FAILED_ATTEMPT))));

        try {
            worker.setSearch(search);
            worker.execute();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            showError("Error", "An error occured during the search activity.");
        }
    }//GEN-LAST:event_jButtonSearchMouseReleased

    private void jCBLockItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBLockItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            switch (this.jCBLock.getSelectedIndex()) {
                case 0:
                    this.locked = BooleanType.BOTH;
                    break;
                case 1:
                    this.locked = BooleanType.TRUE;
                    break;
                case 2:
                    this.locked = BooleanType.FALSE;
                    break;
            }
        }
    }//GEN-LAST:event_jCBLockItemStateChanged

    private void jCBBanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBBanItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            switch (this.jCBBan.getSelectedIndex()) {
                case 0:
                    this.banned = BooleanType.BOTH;
                    break;
                case 1:
                    this.banned = BooleanType.TRUE;
                    break;
                case 2:
                    this.banned = BooleanType.FALSE;
                    break;
            }
        }
    }//GEN-LAST:event_jCBBanItemStateChanged

    private void jCBOnlineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBOnlineItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            switch (this.jCBOnline.getSelectedIndex()) {
                case 0:
                    this.online = BooleanType.BOTH;
                    break;
                case 1:
                    this.online = BooleanType.TRUE;
                    break;
                case 2:
                    this.online = BooleanType.FALSE;
                    break;
            }
        }
    }//GEN-LAST:event_jCBOnlineItemStateChanged

    private void jCBCreationFilterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBCreationFilterItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            switch (this.jCBCreationFilter.getSelectedIndex()) {
                case 0:
                    this.creation = DateType.NONE;
                    this.jDatePickerCreationFrom.setEnabled(false);
                    this.jLabelCreationFrom.setEnabled(false);
                    this.jDatePickerCreationTo.setEnabled(false);
                    this.jLabelCreationTo.setEnabled(false);
                    break;
                case 1:
                    this.creation = DateType.BEFORE;
                    this.jDatePickerCreationFrom.setEnabled(true);
                    this.jLabelCreationFrom.setEnabled(true);
                    this.jDatePickerCreationTo.setEnabled(false);
                    this.jLabelCreationTo.setEnabled(false);
                    break;
                case 2:
                    this.creation = DateType.AFTER;
                    this.jDatePickerCreationFrom.setEnabled(true);
                    this.jLabelCreationFrom.setEnabled(true);
                    this.jDatePickerCreationTo.setEnabled(false);
                    this.jLabelCreationTo.setEnabled(false);
                    break;
                case 3:
                    this.creation = DateType.BETWEEN;
                    this.jDatePickerCreationFrom.setEnabled(true);
                    this.jLabelCreationFrom.setEnabled(true);
                    this.jDatePickerCreationTo.setEnabled(true);
                    this.jLabelCreationTo.setEnabled(true);
                    break;
            }
        }
    }//GEN-LAST:event_jCBCreationFilterItemStateChanged

    private void jCBLoginFilterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBLoginFilterItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            switch (this.jCBLoginFilter.getSelectedIndex()) {
                case 0:
                    this.login = DateType.NONE;
                    this.jDatePickerLoginFrom.setEnabled(false);
                    this.jLabelLoginFrom.setEnabled(false);
                    this.jDatePickerLoginTo.setEnabled(false);
                    this.jLabelLoginTo.setEnabled(false);
                    break;
                case 1:
                    this.login = DateType.BEFORE;
                    this.jDatePickerLoginFrom.setEnabled(true);
                    this.jLabelLoginFrom.setEnabled(true);
                    this.jDatePickerLoginTo.setEnabled(false);
                    this.jLabelLoginTo.setEnabled(false);
                    break;
                case 2:
                    this.login = DateType.AFTER;
                    this.jDatePickerLoginFrom.setEnabled(true);
                    this.jLabelLoginFrom.setEnabled(true);
                    this.jDatePickerLoginTo.setEnabled(false);
                    this.jLabelLoginTo.setEnabled(false);
                    break;
                case 3:
                    this.login = DateType.BETWEEN;
                    this.jDatePickerLoginFrom.setEnabled(true);
                    this.jLabelLoginFrom.setEnabled(true);
                    this.jDatePickerLoginTo.setEnabled(true);
                    this.jLabelLoginTo.setEnabled(true);
                    break;
            }
        }
    }//GEN-LAST:event_jCBLoginFilterItemStateChanged

    private void jButtonResetMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonResetMouseReleased
        Date now = new Date();

        this.jTFName.setText("");

        this.jDatePickerCreationFrom.setDate(now);
        this.jDatePickerCreationTo.setDate(now);
        this.jDatePickerLoginFrom.setDate(now);
        this.jDatePickerLoginTo.setDate(now);

        this.jCBMatch.setSelected(false);

        this.jCBLock.setSelectedIndex(0);
        this.jCBBan.setSelectedIndex(0);
        this.jCBOnline.setSelectedIndex(0);

        this.jCBCreationFilter.setSelectedIndex(0);
        this.jCBLoginFilter.setSelectedIndex(0);

        this.login = DateType.NONE;
        this.jDatePickerLoginFrom.setEnabled(false);
        this.jLabelLoginFrom.setEnabled(false);
        this.jDatePickerLoginTo.setEnabled(false);
        this.jLabelLoginTo.setEnabled(false);

        this.creation = DateType.NONE;
        this.jDatePickerCreationFrom.setEnabled(false);
        this.jLabelCreationFrom.setEnabled(false);
        this.jDatePickerCreationTo.setEnabled(false);
        this.jLabelCreationTo.setEnabled(false);
    }//GEN-LAST:event_jButtonResetMouseReleased

    private void jButtonUnlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUnlockActionPerformed
        unlockAccount();
    }//GEN-LAST:event_jButtonUnlockActionPerformed

    private void jButtonLockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLockActionPerformed
        lockAccount();
    }//GEN-LAST:event_jButtonLockActionPerformed

    private void jButtonUnbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUnbanActionPerformed
        unbanAccount();
    }//GEN-LAST:event_jButtonUnbanActionPerformed

    private void jButtonBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBanActionPerformed
        banAccount();
    }//GEN-LAST:event_jButtonBanActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        deleteAccount();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateActionPerformed
        createAccount();
    }//GEN-LAST:event_jButtonCreateActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        saveAll();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void saveAll() {        
        if(this.jTableAccountsModel.getListEditedAccounts().isEmpty())
        {
            return;
        }
        
        if(askConfirmation("Save", "All the edited data will be lost, are you sure ?") == JOptionPane.CANCEL_OPTION)
        {
            return;
        }
                       
        
        // A list to remember all error accounts.
        List<Account> listErrorAccounts = new ArrayList<>();
        for(Account a : this.jTableAccountsModel.getListEditedAccounts())
        {           
            boolean add = this.jTableAccountsModel.isNewRow(a);
            if(this.as.isValidAccount(a, add))
            {                
                this.as.save(a);                
                
                // If this is a new account, it should be replicated back in the model for future update.
                if(add)
                {
                    this.jTableAccountsModel.removeAddedAccount(a);                      
                    this.jTableAccountsModel.mergeRow(this.as.getAccount(a.getName()));                                                                              
                }
            } else {
                listErrorAccounts.add(a);
                showError("Error", "The system encountered an error while validating the account "+a.getName()+", please verify inputs.");
            }
        }        
        // Finally, we restore the list of edited accounts with the error accounts.
        this.jTableAccountsModel.setListEditedAccounts(listErrorAccounts);
    }
    
    private void createAccount() {        
        this.jTableAccountsModel.addRow(
                new Account(
                        this.ls.getLocaleForString(this.ps.getParameter(ParameterConstants.KEY_DEFAULT_LOCALE)), null, "<Enter a name>", "<Enter a password>", 
                        null, null, null, "<Give valid email>", new Date(), 
                        "0.0.0.0", 0, false, new Date(), false, 
                        null, null, null, null, null));                        
    }
    
    private void deleteAccount() {
        int[] rows = this.jTableAccounts.getSelectedRows();
        if (rows.length == 0) {
            return;
        }

        Arrays.sort(rows);

        for (int i = (rows.length - 1); i >= 0; i--) {
            try {
                if(!this.jTableAccountsModel.isNewRow(this.jTableAccountsModel.getAccount(rows[i])))
                {                    
                    this.as.delete(this.jTableAccountsModel.getAccount(rows[i]).getId());
                }
                this.jTableAccountsModel.removeRow(rows[i]);
            } catch (IllegalArgumentException iae) {
                showError("Oups, an error occured", iae.getMessage());
            }
        }
    }

    private void lockAccount() {
        int[] rows = this.jTableAccounts.getSelectedRows();
        if (rows.length == 0) {
            return;
        }

        Arrays.sort(rows);

        for (int i = (rows.length - 1); i >= 0; i--) {
            try {
                this.as.lockAccount(this.jTableAccountsModel.getAccount(rows[i]).getId());
                this.jTableAccountsModel.getAccount(rows[i]).setLocked(true);
                this.jTableAccountsModel.fireTableRowsUpdated(rows[i], rows[i]);
            } catch (IllegalArgumentException iae) {
                showError("Oups, an error occured", iae.getMessage());
            }
        }
    }

    private void unlockAccount() {
        int[] rows = this.jTableAccounts.getSelectedRows();
        if (rows.length == 0) {
            return;
        }

        Arrays.sort(rows);

        for (int i = (rows.length - 1); i >= 0; i--) {
            try {
                this.as.unlockAccount(this.jTableAccountsModel.getAccount(rows[i]).getId());
                this.jTableAccountsModel.getAccount(rows[i]).setLocked(false);
                this.jTableAccountsModel.fireTableRowsUpdated(rows[i], rows[i]);
            } catch (IllegalArgumentException iae) {
                showError("Oups, an error occured", iae.getMessage());
            }
        }
    }

    private void banAccount() {                
        int[] rows = this.jTableAccounts.getSelectedRows();
        if (rows.length == 0) {
            return;
        }

        Arrays.sort(rows);

        // Fix me, ban account.
        for (int i = (rows.length - 1); i >= 0; i--) {
            try {
                askForBanReason(this.jTableAccountsModel.getAccount(rows[i]).getName());
                if (dialogBan.getCode() == JOptionPane.OK_OPTION) {
                    this.as.banAccount(this.jTableAccountsModel.getAccount(rows[i]).getId(), 1, dialogBan.getReason(), dialogBan.getDuration());
                    this.jTableAccountsModel.fireTableRowsUpdated(rows[i], rows[i]);
                }
                dialogBan.setDuration(DEFAULT_BAN_DURATION);
                dialogBan.setReason("");
            } catch (IllegalArgumentException iae) {
                showError("Oups, an error occured", iae.getMessage());
            }
        }
    }

    private void unbanAccount() {
        int[] rows = this.jTableAccounts.getSelectedRows();
        if (rows.length == 0) {
            return;
        }

        Arrays.sort(rows);

        for (int i = (rows.length - 1); i >= 0; i--) {
            try {
                this.as.unbanAccount(this.jTableAccountsModel.getAccount(rows[i]).getId());
                this.jTableAccountsModel.fireTableRowsUpdated(rows[i], rows[i]);
            } catch (IllegalArgumentException iae) {
                showError("Oups, an error occured", iae.getMessage());
            }
        }
    }

    private void askForBanReason(String name) {
        this.dialogBan.setTitle("Please provide ban informations for: "+name);
        this.dialogBan.setVisible(true);
    }

    private int askConfirmation(String title, String message) {
        return JOptionPane.showConfirmDialog(this,message, title, JOptionPane.OK_CANCEL_OPTION);
    }
    
    private void showWarning(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }

    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBan;
    private javax.swing.JButton jButtonCreate;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonLock;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonUnban;
    private javax.swing.JButton jButtonUnlock;
    private javax.swing.JComboBox jCBBan;
    private javax.swing.JComboBox jCBCreationFilter;
    private javax.swing.JComboBox jCBLocale;
    private javax.swing.JComboBox jCBLock;
    private javax.swing.JComboBox jCBLoginFilter;
    private javax.swing.JCheckBox jCBMatch;
    private javax.swing.JComboBox jCBOnline;
    private javax.swing.JComboBox jCBRealm;
    private org.jdesktop.swingx.JXDatePicker jDatePickerCreationFrom;
    private org.jdesktop.swingx.JXDatePicker jDatePickerCreationTo;
    private org.jdesktop.swingx.JXDatePicker jDatePickerLoginFrom;
    private org.jdesktop.swingx.JXDatePicker jDatePickerLoginTo;
    private javax.swing.JLabel jLabelBan;
    private javax.swing.JLabel jLabelCreationFilter;
    private javax.swing.JLabel jLabelCreationFrom;
    private javax.swing.JLabel jLabelCreationTo;
    private javax.swing.JLabel jLabelLocale;
    private javax.swing.JLabel jLabelLock;
    private javax.swing.JLabel jLabelLoginFilter;
    private javax.swing.JLabel jLabelLoginFrom;
    private javax.swing.JLabel jLabelLoginTo;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelOnline;
    private javax.swing.JLabel jLabelRealm;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelActions;
    private javax.swing.JPanel jPanelControls;
    private javax.swing.JPanel jPanelCreation;
    private javax.swing.JPanel jPanelFilters;
    private javax.swing.JPanel jPanelGeneric;
    private javax.swing.JPanel jPanelLogin;
    private javax.swing.JScrollPane jScrollPaneTableAccounts;
    private javax.swing.JTextField jTFName;
    private javax.swing.JTable jTableAccounts;
    // End of variables declaration//GEN-END:variables
    private final JTableAccountModel jTableAccountsModel = new JTableAccountModel(this.as, this.ps);

    private class SwingWorkerAccount extends SwingWorker<List<Account>, Void> {

        private String search = "";

        public void setSearch(String search) {
            if (search == null) {
                return;
            }

            this.search = search;
        }

        @Override
        protected List<Account> doInBackground() throws Exception {
            return as.getAllAccounts(
                    this.search, creation, jDatePickerCreationFrom.getDate(), 
                    jDatePickerCreationTo.getDate(), login, jDatePickerLoginFrom.getDate(), 
                    jDatePickerLoginTo.getDate(), locked, banned, online, 
                    (Locale) jCBLocale.getSelectedItem(),
                    (Realm) jCBRealm.getSelectedItem());
        }

        @Override
        protected void done() {
            try {
                jTableAccountsModel.setListAccounts(get());
            } catch (InterruptedException ex) {
                showError("Error", "An error occured during the search action.");
            } catch (ExecutionException ex) {
                showError("Error", "An error occured during the search action.");
            }
        }

    }
}
