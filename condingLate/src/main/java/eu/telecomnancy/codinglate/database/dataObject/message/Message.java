package eu.telecomnancy.codinglate.database.dataObject.message;

import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.time.LocalDate;

public class Message {

    private int id;
    private User sender;
    private User receiver;

    private String message;
    private LocalDate date;

    public Message(int id, User sender, User receiver, String message, LocalDate date) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
    }


    // Getters ------------------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getDate() {
        return date;
    }

    // Setters ------------------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
