package com.usernameapp.Exceptions;

/**
 * Created by daniel.tutila on 9/9/16.
 * This exception is used when the user name length is not valid
 *
 */
public class SizeException  extends  Exception{

    public SizeException() {
    }

    public SizeException(String message) {
        super(message);
    }
}
