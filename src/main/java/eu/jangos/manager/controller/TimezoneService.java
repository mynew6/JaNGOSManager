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
import eu.jangos.manager.model.Realmtimezone;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TimezoneService is an abstract implementation for interaction with Timezone entity.
 * @author Warkdev
 * @version v0.1 BETA.
 * @since 12-02-2016
 */
public class TimezoneService {     
    private static final Logger logger = LoggerFactory.getLogger(TimezoneService.class);                   
    
    /**
     * Return the list of all times available in the database.
     * @return A List of timezones corresponding to the content of the database.
     */
    public List<Realmtimezone> getAllRealmTimezone(){
        logger.debug("Returning all timezones");
                
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createCriteria(Realmtimezone.class).addOrder(Order.asc("name")).list();
        } catch (HibernateException he) {
            return null;
        }
    }
        
}
