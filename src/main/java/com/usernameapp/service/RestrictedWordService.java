package com.usernameapp.service;

import com.usernameapp.dao.RestrictedWordDAO;
import com.usernameapp.model.RestrictedWordEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * This class is not used yet, but  the addRestrictedWord  method can be used to add new restricted words to the table
 *
 * Created by daniel.tutila on 9/9/16.
 */
@Service
@Transactional(readOnly = true)
public class RestrictedWordService {
    @Resource
    RestrictedWordDAO restrictedWordDAO;

    public RestrictedWordDAO getRestrictedWordDAO() {
        return restrictedWordDAO;
    }

    public void setRestrictedWordDAO(RestrictedWordDAO restrictedWordDAO) {
        this.restrictedWordDAO = restrictedWordDAO;
    }

    @Transactional(readOnly = false)
    public void addRestrictedWord(RestrictedWordEntity word){

        getRestrictedWordDAO().addRestrictedWord(word);
    }

    public List<RestrictedWordEntity> getRestrictedWords(){
        return getRestrictedWordDAO().getRestrictedWords();
    }


}
