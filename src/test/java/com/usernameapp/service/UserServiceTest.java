package com.usernameapp.service;

import com.usernameapp.Exceptions.SizeException;
import com.usernameapp.dao.UserDAO;
import com.usernameapp.model.UserEntity;
import com.usernameapp.view.model.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by daniel.tutila on 9/10/16.
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/application-context.xml"})
@Transactional*/
//@ContextConfiguration(locations = {"classpath*:/application-context.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public  class UserServiceTest  extends AbstractTransactionalJUnit4SpringContextTests{


    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;

    List<UserEntity> usernames;
    @Before
    public void init() {
        usernames = new ArrayList<UserEntity>();
        UserEntity user1 = new UserEntity();
        user1.setUsername("John001");
        user1.setId(100);
        UserEntity user2 = new UserEntity();
        user2.setUsername("John001");
        user2.setId(1001);
        UserEntity user3 = new UserEntity();
        user3.setUsername("simpson");
        user3.setId(1010);
        UserEntity user4 = new UserEntity();
        user4.setUsername("simpson");
        user4.setId(1100);
        usernames.add(user1);
        usernames.add(user2);
        usernames.add(user3);
        usernames.add(user4);
        //return usernames;
    }




    @Test
    public void testAdd()
    {
        for(UserEntity user : usernames){

            try {
                boolean userExists = userService.getUserNameExits(user.getUsername());
                Result result = userService.addUser(user);

                Assert.assertEquals(result.isValid(), !userExists);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    @Test
    public void testAddMinException()  {
        UserEntity user1 = new UserEntity();
        user1.setUsername("John001");
        user1.setId(100);


        try {
            Result result = userService.addUser(user1);

        } catch (Exception e) {
            e.printStackTrace();

            Assert.assertEquals(e.getMessage().contains("Username must be greater than"), true);
        }


    }



  /*  @Configuration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }

        @Bean
        public UserDAO userDaO() {
            return Mockito.mock(UserDAO.class);
        }
    }*/


}
