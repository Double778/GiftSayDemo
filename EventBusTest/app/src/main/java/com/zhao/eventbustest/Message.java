package com.zhao.eventbustest;

/**
 * Created by 华哥哥 on 16/5/20.
 */
public class Message {
    private String message;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
