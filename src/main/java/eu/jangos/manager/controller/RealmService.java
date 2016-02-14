package eu.jangos.manager.controller;

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
import eu.jangos.manager.hibernate.HibernateUtil;
import eu.jangos.manager.model.Realm;
import eu.jangos.manager.model.Realmtimezone;
import eu.jangos.manager.model.Realmtype;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RealmService is an abstraction layer to interact with Realm entities.
 *
 * @author Warkdev
 * @version v0.1 BETA.
 * @since 11-02-2016
 */
public class RealmService {

    private static final Logger logger = LoggerFactory.getLogger(RealmService.class);

    /**
     * Return the realm information with the corresponding id.
     *
     * @param id The id of the realm to be found.
     * @return A Realm object containing the realm information or null if no
     * matching account is found.
     */
    public Realm getRealm(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Realm realm = (Realm) session.createCriteria(Realm.class)
                    .setFetchMode("realmtimezone", FetchMode.JOIN)
                    .setFetchMode("realmtype", FetchMode.JOIN)
                    .add(Restrictions.eq("id", id))
                    .uniqueResult();
            return realm;
        } catch (HibernateException he) {
            logger.error("There was an error connecting to the database.");
            return null;
        }
    }

    /**
     * Return the list of all realms matching the given criterias.
     *
     * @param name The name of the realm to be found.
     * @param type The type of realm to be found.
     * @param zone The zone of realm to be found.
     * @return A list of realm corresponding to the given filters.
     */
    public List<Realm> getAllRealms(String name, Realmtype type, Realmtimezone zone) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Criteria query = session.createCriteria(Realm.class);

            query.setFetchMode("realmtimezone", FetchMode.JOIN);
            query.setFetchMode("realmtype", FetchMode.JOIN);

            query.add(Restrictions.like("name", name));

            if (!type.getType().equals("ALL")) {
                query.add(Restrictions.eq("realmtype", type));
            }

            if (!zone.getName().equals("ALL")) {
                query.add(Restrictions.eq("realmtimezone", zone));
            }

            return query.list();
        } catch (HibernateException he) {
            logger.error("There was an error connecting to the database.");
            return null;
        }
    }

    /**
     * Provides all realms stored in the database.
     *
     * @return A List of Realms corresponding to all realms stored in the
     * database.
     */
    public List<Realm> getAllRealms() {
        logger.debug("Returning all realms");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createCriteria(Realm.class).addOrder(Order.asc("name")).list();
        } catch (HibernateException he) {
            return null;
        }

    }

    /**
     * Returns the average population of all servers in the database.
     * @return A float number corresponding to the average population.
     */
    public double getAveragePopulation()
    {
        logger.debug("Calculation average population.");
                
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (double) session.createCriteria(Realm.class)
                    .setProjection(Projections.avg("population")).uniqueResult();
        } catch (HibernateException he) {
            return 0;
        }
    }
    
    /**
     * Execute the calculation of the realm population, calculation is then saved into the database.
     * Calculation is: (playerCount / maxPlayerCount * 2)
     * @param realm The realm for which the population should be recalculated.
     */
    public void calculatePopulation(Realm realm)
    {
        if(realm == null)
        {
            return;
        }
        
        float population = (realm.getCountPlayers() + realm.getMaxPlayers()) * 2.0f;
        
        realm.setPopulation(population);
        
        save(realm);
    }
    
    /**
     * Delete the realm with the given id.
     *
     * @param id The id of the realm to be deleted.
     */
    public void delete(int id) {
        Realm realm = getRealm(id);

        if (realm == null) {
            logger.error("A delete action was requested on an unexisting realm.");
            throw new IllegalArgumentException("No realm to delete.");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(realm);
            session.flush();
            session.getTransaction().commit();
            logger.info("Realm " + realm.getName() + " deleted.");
        } catch (HibernateException he) {
            logger.error("There was an issue while deleting realm " + realm.getName());
        }
    }

    /**
     * This method save a realm information into the database.
     *
     * @param realm The realm instance to be saved in the database.
     * @return A realm object representing the created realm. Or null if there
     * was an error while updating the database.
     */
    public Realm save(Realm realm) {
        if (realm == null) {
            logger.error("Realm to save is null.");
            return null;
        }

        Realm r = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            r = (Realm) session.merge(realm);
            session.flush();
            session.getTransaction().commit();
            logger.info("Realm " + realm.getName() + " saved.");
        } catch (HibernateException he) {
            logger.error("There was an issue while performing the save action on " + realm.getName());
        }

        return r;
    }

    /**
     * Checks whether a realm object is valid before storing it into the
     * database.
     *
     * @param realm The realm to be checked.
     * @return True if the realm object is valid.
     */
    public boolean isValid(Realm realm) {

        if (realm == null) {
            return false;
        }

        if (realm.getName() == null
                || realm.getName().isEmpty()
                || realm.getName().replaceAll("[^\\dA-Za-z ]", "").isEmpty()
                || !realm.getName().replaceAll("[^\\dA-Za-z ]", "").equals(realm.getName())) {
            return false;
        }

        if (realm.getAddress() == null
                || realm.getAddress().isEmpty()) {
            return false;
        }

        if (realm.getPort() <= 0
                || realm.getPort() > 65535) {
            return false;
        }

        if (realm.getRealmtimezone() == null) {
            return false;
        }

        if (realm.getRealmtype() == null) {
            return false;
        }

        return true;
    }
}
