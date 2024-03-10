package org.usermicroservice.enumerations;

public enum MessagesError {
    USER_NOT_FOUND("User not found. "),
    USER_NOT_FOUND_WITH_ID_EQUALS("User Not found with id =  "),
    USER_NOT_FOUND_WITH_EMAIL_EQUALS("User Not found with email =  "),
    USER_NOT_FOUND_WITH_USERNAME_EQUALS("User Not found with username =  "),
    USERNAME_IS_REQUIRED("Username is required."),
    FIRSTNAME_IS_REQUIRED("FirsName is required."),
    LASTNAME_IS_REQUIRED("LastName is required."),
    PASSWORD_IS_REQUIRED("Password is required."),
    EMAIL_IS_REQUIRED("Email is required."),
    EMAIL_IS_INVALID("Email is invalid, please check the format."),
    ID_IS_INVALID_EQUALS("ID is invalid. ID = "),
    EMAIL_ALREADY_EXISTS("Email already exists."),
    PHONE_NUMBER_NOT_VALID("Phone Number Not Valid");

    private final String msg;
    MessagesError(String msg){
        this.msg = msg;
    }
    public String getMessage(){
        return this.msg;
    }
}
