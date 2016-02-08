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
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Warkdev
 */
public class AccountServiceTest {
    
    static AccountService as;
    
    public AccountServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        as = new AccountService();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
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
        String name = "";
        DateType createdFilter = DateType.NONE;
        Date createdFrom = null;
        Date createdTo = null;
        DateType loginFilter = DateType.NONE;
        Date loginFrom = null;
        Date loginTo = null;
        BooleanType locked = BooleanType.BOTH;
        BooleanType banned = BooleanType.FALSE;
        BooleanType online = BooleanType.TRUE;
        AccountService instance = new AccountService();        
        List<Account> result = instance.getAllAccounts(name, createdFilter, createdFrom, createdTo, loginFilter, loginFrom, loginTo, locked, banned, online);
        
        for(Account account : result)
        {            
            System.out.println(account.getName());
        }
    }

    /**
     * Test of getAccount method, of class AccountService.
     */
    /**@Test
    public void testGetAccount_int() {
        System.out.println("getAccount");
        int id = 0;
        AccountService instance = new AccountService();
        Account expResult = null;
        Account result = instance.getAccount(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of getAccount method, of class AccountService.
     */
   /** @Test
    public void testGetAccount_String() {
        System.out.println("getAccount");
        String name = "";
        AccountService instance = new AccountService();
        Account expResult = null;
        Account result = instance.getAccount(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
}
