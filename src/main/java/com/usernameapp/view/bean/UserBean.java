package com.usernameapp.view.bean;

import com.usernameapp.Exceptions.SizeException;
import com.usernameapp.model.UserEntity;
import com.usernameapp.service.RestrictedWordService;
import com.usernameapp.service.UserService;
import com.usernameapp.view.model.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

import static org.springframework.web.util.TagUtils.SCOPE_SESSION;

/**
 * Created by daniel.tutila on 9/9/16.
 */
@Scope(SCOPE_SESSION)
@Controller
public class UserBean implements Serializable {
    private String username;
    private Result result;
    private List<UserEntity> userList;
    private boolean showSuggestions;
    @Resource
    UserService userService;

    @Resource
    RestrictedWordService restrictedWordService;

    public UserBean() {
        showSuggestions = false;
    }

    public void addUserName(){
        try{
            UserEntity user = new UserEntity();
            user.setUsername(getUsername());
            result = getUserService().addUser(user);
            if(result.isValid()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
                        "Username saved successfully."));
                userList = null;
                username = null;
                showSuggestions = false;
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error:: ", result.getMessage()));
                showSuggestions = true;
            }

        }
        catch (SizeException e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: ", e.getMessage()));
        }

        catch (Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Unexpected error, please contact your administrator"));
        }


    }

    /*

        Getters and Setters section
     */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public List<UserEntity> getUserList() {
        if(userList == null){
            userList = getUserService().getAllUserNames();
        }
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public RestrictedWordService getRestrictedWordService() {
        return restrictedWordService;
    }

    public void setRestrictedWordService(RestrictedWordService restrictedWordService) {
        this.restrictedWordService = restrictedWordService;
    }

    public boolean isShowSuggestions() {
        return showSuggestions;
    }

    public void setShowSuggestions(boolean showSuggestions) {
        this.showSuggestions = showSuggestions;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
