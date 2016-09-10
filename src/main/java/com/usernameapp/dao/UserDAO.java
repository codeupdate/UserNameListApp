package com.usernameapp.dao;

import com.usernameapp.model.UserEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created by daniel.tutila on 9/9/16.
 */
@Repository
public class UserDAO implements Serializable {
    @Resource
    private SessionFactory sessionFactory;

    /**
     * Get Hibernate Session Factory
     *
     * @return SessionFactory - Hibernate Session Factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Set Hibernate Session Factory
     *
     * @param sessionFactory SessionFactory - Hibernate Session Factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Add a new user name to the table
     * @param user
     * @throws Exception
     */
    public void addUser(UserEntity user) throws Exception {

        getSessionFactory().getCurrentSession().save(user);

    }

    /**
     * -Get all the user when the username param is null
     * -Get a specific username from the table
     * @param username
     * @return a user name list with the specified user or all users in the table
     */
    public List<UserEntity> getUsers(String username){
        if(username != null){
            List list = getSessionFactory().getCurrentSession().createQuery("from UserEntity where username='" + username +"'").list();
            return list;
        }else{
            List list = getSessionFactory().getCurrentSession().createQuery("from UserEntity order by id").list();
            return list;
        }

    }
}
