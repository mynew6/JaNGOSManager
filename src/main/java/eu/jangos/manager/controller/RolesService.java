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
import eu.jangos.manager.model.Roles;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RolesService is an abstract implementation for interaction with Roles entity.
 *
 * @author Warkdev
 * @version v0.1 BETA.
 * @since 18-02-2016
 */
public class RolesService {

    private static final Logger logger = LoggerFactory.getLogger(RolesService.class);

    /**
     * Return the role with the corresponding ID.
     * @param id The ID of the role to be returned.
     * @return A Roles object corresponding to the ID provided, null if no roles matching this ID has been found.
     */
    public Roles getRole(int id) {
        logger.debug("Returning role with id "+id);
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (Roles) session.createCriteria(Roles.class)
                    .add(Restrictions.like("id", id))
                    .uniqueResult();
        } catch (HibernateException he) {
            logger.debug("The role does not exist.");
            return null;
        }
    }

    /**
     * Return the role with the corresponding name.
     * @param name The name of the role to be returned.
     * @return A Roles object corresponding to the name provided, null if no roles matching this name has been found.
     */
    public Roles getRole(String name) {
        logger.debug("Returning role with name "+name);
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (Roles) session.createCriteria(Roles.class)
                    .add(Restrictions.like("name", name))
                    .uniqueResult();
        } catch (HibernateException he) {
            logger.debug("The role does not exist.");
            return null;
        }
    }
    
    /**
     * Return the role, as well as its associated commands, with the corresponding ID.
     * @param id The ID of the role to be returned.
     * @return A Roles object corresponding to the ID provided, null if no roles matching this ID has been found.
     */
    public Roles getRoleWithCommands(int id) {
        logger.debug("Returning role with id "+id+" and commands");
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (Roles) session.createCriteria(Roles.class)
                    .setFetchMode("commandses", FetchMode.JOIN)
                    .add(Restrictions.like("id", id))
                    .uniqueResult();
        } catch (HibernateException he) {
            logger.debug("The role does not exist.");
            return null;
        }
    }
    
    /**
     * Return the list of all roles available in the database.
     *
     * @return A List of roles corresponding to the content of the database.
     */
    public List<Roles> getAllRoles() {
        logger.debug("Returning all roles");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createCriteria(Roles.class)                                      
                    .addOrder(Order.asc("name")).list();
        } catch (HibernateException he) {
            return null;
        }
    }

    /**
     * Check whether the given role already exist in the database.
     *
     * @param name The name of the role to be found.
     * @return True if the role with the given name exist, false otherwise.
     */
    public boolean exists(String name) {
        logger.debug("Checking role existence for the name " + name);
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The parameter name is null or empty.");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (session.createCriteria(Roles.class)
                    .add(Restrictions.like("name", name))
                    .uniqueResult() != null);
        } catch (HibernateException he) {
            logger.debug("The role does not exist.");
            return false;
        }
    }

    /**
     * Persist the role object into the database.
     *
     * @param role The role object to be saved.
     * @return The role object persisted into the database.
     */
    public Roles save(Roles role) {
        Roles r = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            r = (Roles) session.merge(role);
            session.flush();
            session.getTransaction().commit();
            logger.info("Role " + r.getName() + " saved.");
        } catch (HibernateException he) {
            logger.error("There was an issue while performing the save action on " + role.getName());
        }

        return r;
    }

    /**
     * Delete the role with the given id.
     *
     * @param id The id of the role to be deleted.
     */
    public void delete(int id) {
        Roles role = getRole(id);

        if (role == null) {
            logger.error("A delete action was requested on an unexisting role.");
            throw new IllegalArgumentException("No role to delete.");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(role);
            session.flush();
            session.getTransaction().commit();
            logger.info("Role " + role.getName() + " deleted.");
        } catch (HibernateException he) {
            logger.error("There was an issue while deleting role " + role.getName());
        }
    }
}
