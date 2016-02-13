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
import eu.jangos.manager.model.Account;
import eu.jangos.manager.model.Realm;
import eu.jangos.manager.model.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Warkdev
 */
public class AccountServiceTest {

    static AccountService as;
    static SimpleDateFormat sdf;
    String name;
    DateType createdFilter;
    Date createdFrom;
    Date createdTo;
    DateType loginFilter;
    Date loginFrom;
    Date loginTo;
    BooleanType locked;
    BooleanType banned;
    BooleanType online;
    Realm realm;
    Locale locale;

    public AccountServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        as = new AccountService();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        name = "%";
        createdFilter = DateType.NONE;
        createdFrom = null;
        createdTo = null;
        loginFilter = DateType.NONE;
        loginFrom = null;
        loginTo = null;
        locked = BooleanType.BOTH;
        banned = BooleanType.BOTH;
        online = BooleanType.BOTH;
        locale = new Locale();
        locale.setLocale("ALL");
        realm = new Realm();
        realm.setName("ALL");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllAccounts method, of class AccountService.
     */
    @Test
    public void testGetAllAccounts() {
        System.out.println("getAllAccounts");

        List<String> l = getResultList();

        // From the test data, we know that the following accounts will be returned:
        // - TEST/ONLINE/BANNED/FAILED/LOCK        
        assertThat(l, hasItems("TEST", "ONLINE", "BANNED", "FAILED", "LOCK"));
    }

    @Test
    public void testGetAllBannedAccounts() {
        System.out.println("testGetAllBannedAccounts");

        banned = BooleanType.TRUE;

        List<String> l = getResultList();

        // From the test data, we know that the following account will be returned:
        // - BANNED
        assertThat(l, hasItems("BANNED"));
        assertThat(l, not(hasItems("TEST", "ONLINE", "FAILED", "LOCK")));
    }

    @Test
    public void testGetAllNotBannedAccounts() {
        System.out.println("testGetAllNotBannedAccounts");

        banned = BooleanType.FALSE;

        List<String> l = getResultList();

        // From the test data, we know that the following account will be returned:
        // - TEST/ONLINE/FAILED/LOCK  
        assertThat(l, not(hasItems("BANNED")));
        assertThat(l, hasItems("TEST", "ONLINE", "FAILED", "LOCK"));
    }

    @Test
    public void testGetAllLockedAccounts() {
        System.out.println("testGetAllLockedAccounts");

        locked = BooleanType.TRUE;

        List<String> l = getResultList();

        // From the test data, we know that the following account will be returned:
        // - LOCK
        assertThat(l, hasItems("LOCK"));
        assertThat(l, not(hasItems("TEST", "ONLINE", "FAILED", "BANNED")));
    }

    @Test
    public void testGetAllNotLockedAccounts() {
        System.out.println("testGetAllNotLockedAccounts");

        locked = BooleanType.FALSE;

        List<String> l = getResultList();

        // From the test data, we know that the following account will be returned:
        // - TEST/ONLINE/FAILED/BANNED  
        assertThat(l, not(hasItems("LOCK")));
        assertThat(l, hasItems("TEST", "ONLINE", "FAILED", "BANNED"));
    }

    @Test
    public void testGetAllOnlineAccounts() {
        System.out.println("testGetAllLockedAccounts");

        online = BooleanType.TRUE;

        List<String> l = getResultList();

        // From the test data, we know that the following account will be returned:
        // - ONLINE
        assertThat(l, hasItems("ONLINE"));
        assertThat(l, not(hasItems("TEST", "LOCK", "FAILED", "BANNED")));
    }

    @Test
    public void testGetAllOfflineAccounts() {
        System.out.println("testGetAllNotLockedAccounts");

        online = BooleanType.FALSE;

        List<String> l = getResultList();

        // From the test data, we know that the following account will be returned:
        // - TEST/BANNED/FAILED/LOCK  
        assertThat(l, not(hasItems("ONLINE")));
        assertThat(l, hasItems("TEST", "LOCK", "FAILED", "BANNED"));
    }

    @Test
    public void testGetAllAccountCreatedAfter() throws ParseException {
        System.out.println("testGetAllAccountCreatedAfter");

        createdFilter = DateType.AFTER;
        createdFrom = sdf.parse("02/02/2016");

        List<String> l = getResultList();

        // From the test data, we know that the following account will be returned:
        // - ONLINE  
        assertThat(l, hasItems("ONLINE"));
        assertThat(l, not(hasItems("TEST", "LOCK", "FAILED", "BANNED")));
    }

    @Test
    public void testGetAllAccountCreatedBefore() throws ParseException {
        System.out.println("testGetAllAccountCreatedBefore");

        createdFilter = DateType.BEFORE;
        createdFrom = sdf.parse("02/02/2016");

        List<String> l = getResultList();

        // From the test data, we know that the following account will be returned:
        // - TEST/BANNED/FAILED/LOCK  
        assertThat(l, not(hasItems("ONLINE")));
        assertThat(l, hasItems("TEST", "LOCK", "FAILED", "BANNED"));
    }

    @Test
    public void testGetAllAccountCreatedBetween() throws ParseException {
        System.out.println("testGetAllAccountCreatedBetween");

        createdFilter = DateType.BETWEEN;
        createdFrom = sdf.parse("08/03/2015");
        createdTo = sdf.parse("15/02/2016");

        List<String> l = getResultList();

        // From the test data, we know that the following account will be returned:
        // - ONLINE/FAILED
        assertThat(l, hasItems("ONLINE", "FAILED"));
        assertThat(l, not(hasItems("TEST", "LOCK", "BANNED")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAllAccountNameNull() throws ParseException {
        System.out.println("testGetAllAccountNameNull");

        name = null;        

        getResultList();        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllAccountNameEmpty() throws ParseException {
        System.out.println("testGetAllAccountNameEmpty");

        name = "";        

        getResultList();        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllAccountWrongCreationDatesAfter() throws ParseException {
        System.out.println("testGetAllAccountWrongCreationDatesAfter");

        createdFilter = DateType.AFTER;
        createdFrom = null;

        getResultList();        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllAccountWrongCreationDatesBefore() throws ParseException {
        System.out.println("testGetAllAccountWrongCreationDatesBefore");

        createdFilter = DateType.BEFORE;
        createdFrom = null;

        getResultList();        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllAccountWrongCreationDatesBetween() throws ParseException {
        System.out.println("testGetAllAccountWrongCreationDatesBetween");

        createdFilter = DateType.BETWEEN;
        createdFrom = sdf.parse("08/03/2015");
        createdTo = null;

        getResultList();        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllAccountWrongCreationDatesBetween2() throws ParseException {
        System.out.println("testGetAllAccountWrongCreationDatesBetween2");

        createdFilter = DateType.BETWEEN;
        createdFrom = null;
        createdTo = sdf.parse("08/03/2015");

        getResultList();        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllAccountWrongLoginDatesAfter() throws ParseException {
        System.out.println("testGetAllAccountWrongLoginDatesAfter");

        loginFilter = DateType.AFTER;
        loginFrom = null;

        getResultList();        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllAccountWrongLoginDatesBefore() throws ParseException {
        System.out.println("testGetAllAccountWrongLoginDatesBefore");

        loginFilter = DateType.BEFORE;
        loginFrom = null;

        getResultList();        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllAccountWrongLoginDatesBetween() throws ParseException {
        System.out.println("testGetAllAccountWrongLoginDatesBetween");

        loginFilter = DateType.BETWEEN;
        loginFrom = sdf.parse("08/03/2015");
        loginTo = null;

        getResultList();        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllAccountWrongLoginDatesBetween2() throws ParseException {
        System.out.println("testGetAllAccountWrongCreationDatesBetween2");

        loginFilter = DateType.BETWEEN;
        loginFrom = null;
        loginTo = sdf.parse("08/03/2015");

        getResultList();        
    }
    
    
    private List<String> getResultList() {
        List<Account> result = as.getAllAccounts(name, createdFilter, createdFrom, createdTo, loginFilter, loginFrom, loginTo, locked, banned, online, locale, realm);
        List<String> l = new ArrayList<>();

        for (Account account : result) {
            l.add(account.getName());
        }

        return l;
    }

    /**
     * Test of getAccount method, of class AccountService.
     */
    /**
     * @Test public void testGetAccount_int() {
     * System.out.println("getAccount"); int id = 0; AccountService instance =
     * new AccountService(); Account expResult = null; Account result =
     * instance.getAccount(id); assertEquals(expResult, result); // TODO review
     * the generated test code and remove the default call to fail. fail("The
     * test case is a prototype."); }
     */
    /**
     * Test of getAccount method, of class AccountService.
     */
    /**
     * @Test public void testGetAccount_String() {
     * System.out.println("getAccount"); String name = ""; AccountService
     * instance = new AccountService(); Account expResult = null; Account result
     * = instance.getAccount(name); assertEquals(expResult, result); // TODO
     * review the generated test code and remove the default call to fail.
     * fail("The test case is a prototype."); }
     */
}
