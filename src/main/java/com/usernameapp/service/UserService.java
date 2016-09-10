package com.usernameapp.service;

import com.usernameapp.Exceptions.SizeException;
import com.usernameapp.dao.RestrictedWordDAO;
import com.usernameapp.dao.UserDAO;
import com.usernameapp.model.RestrictedWordEntity;
import com.usernameapp.model.UserEntity;
import com.usernameapp.view.model.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by daniel.tutila on 9/9/16.
 */
@Service
@Transactional(readOnly = true)
public class UserService {
    //max size of the username field
    private int maxSize;
    //min size of the username field
    private int minSize;
    //maximun number of iterations allowed  to fill the suggestion list
    private int maxIterations;
    //maximum size of the suggestion list
    private int suggestionMaxSize;
    //maximun size of the ramdom string
    private int randomStringSize;

    @Resource
    UserDAO userDAO;

    @Resource
    RestrictedWordDAO restrictedWordDAO;

    public RestrictedWordDAO getRestrictedWordDAO() {
        return restrictedWordDAO;
    }

    public void setRestrictedWordDAO(RestrictedWordDAO restrictedWordDAO) {
        this.restrictedWordDAO = restrictedWordDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public int getSuggestionMaxSize() {
        return suggestionMaxSize;
    }

    public void setSuggestionMaxSize(int suggestionMaxSize) {
        this.suggestionMaxSize = suggestionMaxSize;
    }

    public int getRandomStringSize() {
        return randomStringSize;
    }

    public void setRandomStringSize(int randomStringSize) {
        this.randomStringSize = randomStringSize;
    }

    /**
     * This method add a new record to the user Table
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public Result addUser(UserEntity user) throws Exception {
        Result result = new Result();
        if(user.getUsername().length() < minSize){
            throw new SizeException("Username must be greater than "+minSize+" characters");
        }
        if(user.getUsername().length() > maxSize){
            throw new SizeException("Username maximum size is "+maxSize+" characters");
        }
        for(RestrictedWordEntity restrictedWord: getRestrictedWordDAO().getRestrictedWords()){
            if(user.getUsername().contains(restrictedWord.getRestrictedWord())){
                result.setValid(false);
                result.setMessage("Username contains a restricted word ["+restrictedWord.getRestrictedWord()+"], please enter a new value");
                List <String> list =new ArrayList(getUserNameSuggestions(user.getUsername()));
                Collections.sort(list);
                result.setSuggestions(list);
                return result;
            }
        }
        if(getUserDAO().getUsers(user.getUsername()).isEmpty()) {
            getUserDAO().addUser(user);
            result.setValid(true);
        }else{
            result.setValid(false);
            List <String> list =new ArrayList(getUserNameSuggestions(user.getUsername()));
            Collections.sort(list);
            result.setSuggestions(list);
            result.setMessage("Username " +user.getUsername() + " already exists.");

        }

        return result;
    }

    /**
     *  Get all the users from the user table
     * @return
     */
    public List<UserEntity> getAllUserNames(){
        return getUserDAO().getUsers(null);
    }

    /**
     *  Return a specific user
     * @param username
     * @return the usernameEntity or null if username is not found
     */
    public boolean getUserNameExits( String username){
        List<UserEntity> list = getUserDAO().getUsers(username);
        if(list == null) return false;
        if(list.size()>= 1)
            return true;
        else
            return  false;

    }

    /**
     * This method generates a list of usernames
     *
     * @param username string used to generate the suggested usernames
     * @return Set with the suggested usernames
     */
    private Set<String> getUserNameSuggestions(String username){
        Set<String> suggestionList = new HashSet<String>();
        String base = username;

        //the suggested name length must be valid
        if((maxSize-randomStringSize)< username.length()){
            base = base.substring(0,maxSize-randomStringSize);
        }
        List<RestrictedWordEntity> restrictedList = getRestrictedWordDAO().getRestrictedWords();
        List<UserEntity>  userList = getUserDAO().getUsers(null);
        int iteration = 0;
        while( iteration < maxIterations) {
            for (int i = 0; i < suggestionMaxSize; i++) {
                String tmpUserName = base + getRandomString();
                boolean valid = true;
                //validating the max size
                if((maxSize)< username.length()){
                    tmpUserName = tmpUserName.substring(0,maxSize);
                }
                //validating the suggested name, must not contain a restricted word or a taken username
                for(RestrictedWordEntity restrictedWord: restrictedList){
                    if(tmpUserName.contains(restrictedWord.getRestrictedWord())){
                       valid = false;
                        break;
                    }
                }
                for(UserEntity takenUserName: userList){
                    if(tmpUserName.equalsIgnoreCase(takenUserName.getUsername())){
                        valid = false;
                        break;
                    }
                }

                if(valid)suggestionList.add(tmpUserName);
                if(suggestionList.size()>= suggestionMaxSize){
                    return suggestionList;
                }
            }
            iteration =  iteration + 1;
        }

        return suggestionList;

    }

    /**
     * Generates a random String
     * see http://stackoverflow.com/questions/5025651/java-randomly-generate-distinct-names
     * @return random string
     */
    private String getRandomString(){
        String availabeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
        Random rand = new Random();
        Set<String> identifiers = new HashSet<String>();
        StringBuilder sb = new StringBuilder();


        while(sb.toString().length() == 0) {
            int length = rand.nextInt(randomStringSize) +1;
            for(int i = 0; i < length; i++)
                sb.append(availabeChars.charAt(rand.nextInt(availabeChars.length())));
            if(identifiers.contains(sb.toString()))
                sb = new StringBuilder();
        }
        return sb.toString();
    }
}
