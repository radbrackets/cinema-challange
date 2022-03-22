package net.skarbek.cinemachallange.api.model;

public class ActionResult {
    enum Status{
        SUCCESS,FAIL
    }

    private final Status status;
    private final String message;

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    private ActionResult(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ActionResult success(){
        return new ActionResult(Status.SUCCESS, null);
    }

    public static ActionResult fail(String message){
        return new ActionResult(Status.FAIL, message);
    }
}
