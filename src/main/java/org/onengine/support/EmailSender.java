package org.onengine.support;

public class EmailSender {
    public void send(String address, String msg) {
        System.out.println("sending email to "+address+" with message: " + msg);
    }
}