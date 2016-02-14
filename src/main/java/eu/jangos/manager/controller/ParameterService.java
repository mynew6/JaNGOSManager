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
import eu.jangos.manager.model.Parameter1;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ParameterService is an abstraction layer providing access to Parameters
 * stored in the database.
 *
 * @author Warkdev
 * @version v0.1 BETA
 * @since 12-02-2016
 */
public class ParameterService {

    private static final Logger logger = LoggerFactory.getLogger(ParameterService.class);

    /**
     * Returns the parameter object corresponding to the asked key.
     * @param key The key to be found in the database.
     * @return The corresponding parameter. Null if there is no such parameter key into the database.
     */
    public Parameter1 getParameterObject(String key) {
        if (key == null || key.isEmpty()) {
            logger.debug("Key parameter is empty, returning null.");
            return null;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Parameter1 parameter = (Parameter1) session.createCriteria(Parameter1.class).add(Restrictions.eq("param", key)).uniqueResult();

            if (parameter == null) {
                return null;
            }

            return parameter;
        } catch (HibernateException he) {
            return null;
        }
    }
    
    /**
     * Returns the parameter corresponding to the asked key.
     *
     * @param key The key to retrieve from the database.
     * @return The parameter corresponding to the parameter key in a String
     * format or null if the key is empty/not found.
     */
    public String getParameter(String key) {
        if (key == null || key.isEmpty()) {
            logger.debug("Key parameter is empty, returning null.");
            return null;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Parameter1 parameter = (Parameter1) session.createCriteria(Parameter1.class).add(Restrictions.eq("param", key)).uniqueResult();

            if (parameter == null) {
                return null;
            }

            return parameter.getVal();
        } catch (HibernateException he) {
            return null;
        }
    }

    /**
     * Persist the parameter provided in parameter into the database.
     *
     * @param parameter The parameter to save into the database.
     * @return The persisted parameter.
     */
    public Parameter1 save(Parameter1 parameter) {
        if (parameter == null) {
            logger.error("Parameter to save is null.");
            return null;
        }

        Parameter1 p = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            p = (Parameter1) session.merge(parameter);
            session.flush();
            session.getTransaction().commit();
            logger.info("Parameter " + parameter.getParam() + " saved.");
        } catch (HibernateException he) {
            logger.error("There was an issue while performing the save action on " + parameter.getParam());
        }

        return p;
    }
}
