package org.tasksmicroservice.enumerations;

public enum MessageError {
    TASK_NOT_FOUND("Task not found. "),
    TASK_NOT_FOUND_WITH_ID_EQUALS("Task Not found with id =  "),
    USER_NOT_FOUND("User not found. "),
    USER_NOT_FOUND_WITH_ID_EQUALS("User Not found with id =  "),
    STATUS_IS_REQUIRED("Status is required."),
    TITLE_IS_REQUIRED("Title is required."),
    DUEDATE_IS_INVALID("DueDate is not valid.");
    private final String msg;
    MessageError(String msg){
        this.msg = msg;
    }
    public String getMessage(){
        return this.msg;
    }
}
