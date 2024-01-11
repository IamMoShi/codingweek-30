package eu.telecomnancy.codinglate.database.dataObject.message;

import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.time.LocalDateTime;

public class Message {

    private int id;
    private Person sender;
    private Person receiver;

    private String message;
    private LocalDateTime date;

    public Message(int id, Person sender, Person receiver, String message, LocalDateTime date) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
    }

    public Message(Person sender, Person receiver, String message, LocalDateTime date) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
    }


    // Getters ------------------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public Person getSender() {
        return sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    // Setters ------------------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}
