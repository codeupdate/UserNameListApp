package com.usernameapp.view.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel.tutila on 9/9/16.
 */
public class Result {
    private boolean valid;
    private List<String> suggestions;
    private String message;
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getSuggestions() {
        if(suggestions == null) suggestions = new ArrayList<String>();
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
