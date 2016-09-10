package com.usernameapp.dao;

import com.usernameapp.model.RestrictedWordEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by daniel.tutila on 9/9/16.
 */
@Repository
public class RestrictedWordDAO {

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


    public void addRestrictedWord(RestrictedWordEntity word){
        getSessionFactory().getCurrentSession().save(word);

    }

    public List<RestrictedWordEntity> getRestrictedWords(){

            List list = getSessionFactory().getCurrentSession().createQuery("from RestrictedWordEntity").list();
            return list;


    }

}
