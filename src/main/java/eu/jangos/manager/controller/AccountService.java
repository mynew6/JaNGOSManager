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
package eu.jangos.manager.controller;

import eu.jangos.manager.controller.filters.BooleanType;
import eu.jangos.manager.controller.filters.DateType;
import eu.jangos.manager.hibernate.HibernateUtil;
import eu.jangos.manager.model.Account;
import eu.jangos.manager.utils.Utils;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AccountService is the controller responsible to apply the account business
 * logic and make the link to the database.
 *
 * @author Warkdev
 * @version v0.1 BETA
 */
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final BannedAccountService bas = new BannedAccountService();

    /**
     * Provides access to the list of all accounts matching the parameters.
     *
     * The DateFilter works the same way: - NONE does not apply any filter. -
     * BEFORE & AFTER uses only the "From" date as filter. - BETWEEN uses both
     * "From" & "To" dates as filters.
     *
     * @param name The name of the accounts to be found. Can contain %.
     * @param createdFilter The filter type for the creation date.
     * @param createdFrom The first creation date filter.
     * @param createdTo The second creation date filter.
     * @param loginFilter The filter type for the login date.
     * @param loginFrom The first login date filter.
     * @param loginTo The second login date filter.
     * @param locked The filter type for the lock value.
     * @param banned The filter type for the ban value.
     * @param online The filter type for the online value.
     * @return A List of Accounts matching the criterias.
     */
    public List<Account> getAllAccounts(String name,
            DateType createdFilter, Date createdFrom, Date createdTo,
            DateType loginFilter, Date loginFrom, Date loginTo,
            BooleanType locked, BooleanType banned, BooleanType online) {

        boolean error = false;
        String message = "You must enter the following parameters:\n";

        // Checking name input.
        if (name == null || name.isEmpty()) {
            logger.error("The parameter name is null or empty");
            message += "- A name\n";
            error = true;
        }

        // Checking dates input.
        if ((createdFilter != DateType.NONE && createdFrom == null)
                || (createdFilter == DateType.BETWEEN && createdTo == null)
                || (loginFilter != DateType.NONE && loginFrom == null)
                || (loginFilter == DateType.BETWEEN && loginTo == null)) {
            logger.error("A date filter has been requested while the date values are incorrect");
            message += "- Valid dates when selecting a date filter\n";
            error = true;
        }

        if (error) {
            throw new IllegalArgumentException(message);
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Criteria query = session.createCriteria(Account.class, "acc");

            query.setFetchMode("locale", FetchMode.JOIN);
            query.setFetchMode("realm", FetchMode.JOIN);

            query.add(Restrictions.like("name", name));

            // This ban check is generating 2 SQL queries while, in SQL, you could do it in one.
            // Limitations of the criteria API.
            switch (banned) {
                case TRUE:
                    // First, we get the list of IDs for the accounts that are banned.
                    Criteria getBannedQuery = session.createCriteria(Account.class)
                            .setProjection(Projections.distinct(Projections.property("id")))
                            .createCriteria("bannedaccountsForFkBannedaccount", "ban", JoinType.LEFT_OUTER_JOIN)
                            .add(Restrictions.eq("ban.active", true));

                    // Then we add these IDs to the query.
                    query.add(Restrictions.in("id", getBannedQuery.list()));
                    break;
                case FALSE:
                    // First, we get the list of ID for the accounts that are not banned.
                    Criteria getNotBanQuery = session.createCriteria(Account.class)
                            .setProjection(Projections.distinct(Projections.property("id")))
                            .createCriteria("bannedaccountsForFkBannedaccount", "ban", JoinType.LEFT_OUTER_JOIN)
                            .add(Restrictions.or(Restrictions.eq("ban.active", false), Restrictions.isNull("ban.active")));

                    // Then we add these IDs to the query.
                    query.add(Restrictions.in("id", getNotBanQuery.list()));
                    break;
            }

            query = Utils.applyDateFilter(query, "creation", createdFilter, createdFrom, createdTo);
            query = Utils.applyDateFilter(query, "lastlogin", loginFilter, loginFrom, loginTo);
            query = Utils.applyBooleanFilter(query, "locked", locked);
            query = Utils.applyBooleanFilter(query, "online", online);

            return query.list();
        } catch (HibernateException he) {
            logger.error("There was an error connecting to the database.");
            return null;
        }
    }

    /**
     * Returns whether an account is banned.
     *
     * @param account The account to be checked in database.
     * @return true if the account is banned, false otherwise.
     */
    public boolean isAccountBanned(Account account) {
        if (account == null) {
            logger.error("Account trying to login is null.");
            return true;
        }

        return (this.bas.isAccountBanned(account));
    }

    /**
     * Returns the account corresponding to the given id.
     *
     * @param id The id of the account to be found.
     * @return The account corresponding to the given id. Null if the account is
     * not found.
     */
    public Account getAccount(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Account account = (Account) session.createCriteria(Account.class)
                    .add(Restrictions.eq("id", id))                    
                    .uniqueResult();
            return account;
        } catch (HibernateException he) {
            logger.error("There was an error connecting to the database.");
            return null;
        }
    }

    /**
     * Returns the account corresponding to the given name. The name must
     * contain only alphanumeric values. Prefer this method to the
     * getAllAccounts if your only criteria is a specific name.
     *
     * @param name The name of the account to be found.
     * @return The account corresponding to the given name. Null if the account
     * is not found.
     */
    public Account getAccount(String name) {
        if (name == null || name.isEmpty()) {
            logger.error("The account name is null or empty.");
            throw new IllegalArgumentException("The account name is null or empty");
        }

        if (!name.matches("[a-zA-Z0-9]+")) {
            logger.error("The account name must contain only alphanumeric values.");
            throw new IllegalArgumentException("The account name must contain only alphanumeric values");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Account account = (Account) session.createCriteria(Account.class)
                    .add(Restrictions.like("name", name))
                    .uniqueResult();
            return account;
        } catch (HibernateException he) {
            logger.error("There was an error connecting to the database.");
            return null;
        }
    }

    /**
     * Lock the account with the given id.
     *
     * @param id The id of the account to be locked.
     */
    public void lockAccount(int id) {
        Account account = getAccount(id);

        if (account == null) {
            logger.error("A lock action was requested on an unexisting account.");
            throw new IllegalArgumentException("No account to lock.");
        }

        account.setLocked(true);

        update(account);
    }

    /**
     * Unlock the account with the given id.
     *
     * @param id The id of the account to be unlocked.
     */
    public void unlockAccount(int id) {
        Account account = getAccount(id);

        if (account == null) {
            logger.error("An unlock action was requested on an unexisting account.");
            throw new IllegalArgumentException("No account to unlock.");
        }

        account.setLocked(false);
        
        update(account);
    }

    /**
     * This method will ban the account given in parameter.
     *
     * @param id ID of the account to ban.
     * @param bannedBy The id of the account of the banisher.
     * @param reason The reason why this account is ban.
     * @param days The number of days before this account will be unban. 0 means
     * an unlimited duration.
     */
    public void banAccount(int id, int bannedBy, String reason, int days) {
        Account account = getAccount(id);
        Account banisher = getAccount(id);

        if (account == null) {
            logger.error("A ban action was requested on an unexisting account.");
            throw new IllegalArgumentException("No account to ban.");
        }

        if (banisher == null) {
            logger.error("A ban action was requested by an unknown banisher.");
            throw new IllegalArgumentException("No banisher account.");
        }
        // If an account is already banned, the duration is renewed.

        if (reason == null || reason.isEmpty()) {
            logger.error("The ban reason is null or empty.");
            throw new IllegalArgumentException("You must provide a ban reason.");
        }

        if (days < 0) {
            logger.error("The ban duration is negative.");
            throw new IllegalArgumentException("The ban duration cannot be lower than 0.");
        }

        this.bas.ban(account, banisher, reason, days);
    }

    /**
     * This method will unban the account for which the id is given in parameter.
     *
     * @param id The id of the account to be ban.
     */
    public void unbanAccount(int id) {
        Account account = getAccount(id);
        
        if (account == null) {
            logger.error("An unban action was requested on an unexisting account.");
            throw new IllegalArgumentException("No account to unban.");
        }
        
        this.bas.unban(account);
    }

    /**
     * It will check that a single account with a given named exists in the
     * dabase.
     *
     * @param name The name of the account used to login.
     * @return true if an account with the given name exists in the database,
     * false otherwise.
     */
    public boolean checkExistence(String name) {
        // Empty names are not allowed.
        if (name == null || name.isEmpty()) {
            logger.error("The account name is null or empty.");
            return false;
        }

        if (!name.matches("[a-zA-Z0-9]+")) {
            logger.error("The account name must contain only alphanumeric values.");
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (session.createCriteria(Account.class).add(Restrictions.like("name", name)).uniqueResult() != null);
        } catch (HibernateException he) {
            logger.error("There was an error connecting to the database.");
            return false;
        }
    }

    /**
     * Delete the account with the given id.
     * @param id The id of the account to be deleted.
     */
    public void delete(int id) {
        Account account = getAccount(id);
        
        if (account == null) {
            logger.error("A delete action was requested on an unexisting account.");
            throw new IllegalArgumentException("No account to delete.");
        }
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(account);            
            session.flush();
            session.getTransaction().commit();
            logger.info("Account " + account.getName() + " deleted.");
        } catch (HibernateException he) {
            logger.error("There was an issue while deleting account " + account.getName());
        }
    }
    
    /**
     * This method updates the account information into the database.
     *
     * @param account The account to update in the dabatase.
     */
    private void update(Account account) {
        if (account == null) {
            logger.error("Account to update is null.");
            return;
        }

        if (!checkExistence(account.getName())) {
            logger.error("Account to update does not exist.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(account);
            session.flush();
            session.getTransaction().commit();
            logger.info("Account " + account.getName() + " updated.");
        } catch (HibernateException he) {
            logger.error("There was an issue while updating account " + account.getName());
        }
    }
}
