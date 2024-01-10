package eu.telecomnancy.codinglate.database.dataObject.offer;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;
import eu.telecomnancy.codinglate.database.dataObject.enums.WaitingStatus;
import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.time.LocalDateTime;

public class Waiting extends DataObject {
    private int id;
    private Offer offer;
    private User user;
    private LocalDateTime askingDate;
    private LocalDateTime startingDate;
    private LocalDateTime endingDate;
    private WaitingStatus status;

    // CONSTRUCTORS -------------------------------------------------------------------------------

    public Waiting(int id, Offer offer, User user, LocalDateTime askingDate, LocalDateTime startingDate, LocalDateTime endingDate, WaitingStatus status) {
        setId(id);
        setOffer(offer);
        setUser(user);
        setAskingDate(askingDate);
        setStartingDate(startingDate);
        setEndingDate(endingDate);
        setStatus(status);
    }

    public Waiting(Offer offer, User user, LocalDateTime askingDate, LocalDateTime startingDate, LocalDateTime endingDate) {
        this(-1, offer, user, askingDate, startingDate, endingDate, WaitingStatus.PENDING);
    }

    // GETTERS ------------------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getAskingDate() {
        return askingDate;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    public WaitingStatus getStatus() {
        return status;
    }

    // SETTERS ------------------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAskingDate(LocalDateTime askingDate) {
        this.askingDate = askingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    public void setStatus(WaitingStatus status) {
        this.status = status;
    }


}
