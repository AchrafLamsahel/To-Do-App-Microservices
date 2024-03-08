package org.usermicroservice.enumerations;

public enum ErrorMessage {
    USER_NOT_FOUND("Utilisateur introuvable"),
    PHONE_NUMBER_NOT_VALID("Phone Number Not Valid"),
    FIRST_NAME_IS_NULL("Phone Number Not Valid"),
    EMAIL_NOT_VALID("Email Not Valid"),
    LAST_NAME_IS_NULL("LastName is null"),
    USER_INPUT_NOT_VALID("User Input Not Valid ! ");

    private final String msg;
    ErrorMessage(String msg){
        this.msg = msg;
    }
    public String getMessage(){
        return this.msg;
    }
}
