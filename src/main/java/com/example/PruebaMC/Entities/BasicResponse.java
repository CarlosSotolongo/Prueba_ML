package com.example.PruebaMC.Entities;

public abstract class BasicResponse {
    private String message;
    private boolean status;

    public void defaultSuccess(){
        status = true;
        message = "Success";
    }

    public void setFailure(String message){
        status = false;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
