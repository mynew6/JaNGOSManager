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
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RealmService is an abstraction layer to interact with Realm entities.
 * @author Warkdev
 * @version v0.1 BETA.
 * @since 11-02-2016
 */
public class RealmService {
    private static final Logger logger = LoggerFactory.getLogger(RealmService.class);        
    
    /**
     * Return the list of all realms matching the given criterias.
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
            
            if(!type.getType().equals("ALL"))
            {
                query.add(Restrictions.eq("realmtype", type));
            }
            
            if(!zone.getName().equals("ALL"))
            {
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
     * @return A List of Realms corresponding to all realms stored in the database.
     */
    public List<Realm> getAllRealms(){
        logger.debug("Returning all realms");
                
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createCriteria(Realm.class).addOrder(Order.asc("name")).list();
        } catch (HibernateException he) {
            return null;
        }
                
    }
}
