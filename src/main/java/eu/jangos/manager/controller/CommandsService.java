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
import eu.jangos.manager.model.Commands;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CommandsService is an abstract implementation for interaction with Commands entity.
 * @author Warkdev
 * @version v0.1 BETA.
 * @since 18-02-2016
 */
public class CommandsService {     
    private static final Logger logger = LoggerFactory.getLogger(CommandsService.class);                   
    
    /**
     * Return the list of all commands available in the database.
     * @return A List of Commands corresponding to the content of the database.
     */
    public List<Commands> getAllCommands(){
        logger.debug("Returning all commands");
                
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createCriteria(Commands.class)
                    .add(Restrictions.not(Restrictions.like("name", "all")))
                    .addOrder(Order.asc("name")).list();
        } catch (HibernateException he) {
            return null;
        }
    }
    
    /**
     * Return the root command object from the database.
     * @return A Commands object corresponding to the root command.
     */
    public Commands getRootCommands() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (Commands) session.createCriteria(Commands.class)
                    .add(Restrictions.like("name", "all"))
                    .addOrder(Order.asc("name")).uniqueResult();
        } catch (HibernateException he) {
            return null;
        }
    }
}    