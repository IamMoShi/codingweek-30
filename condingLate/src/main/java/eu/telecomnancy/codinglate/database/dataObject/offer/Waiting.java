package eu.telecomnancy.codinglate.database.dataObject.offer;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;
import eu.telecomnancy.codinglate.database.dataObject.enums.WaitingStatus;
import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.time.LocalDate;

public class Waiting extends DataObject {
    private int id;
    private Offer offer;
    private User user;
    private LocalDate askingDate;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private WaitingStatus status;

    // CONSTRUCTORS -------------------------------------------------------------------------------

    public Waiting(int id, Offer offer, User user, LocalDate askingDate, LocalDate startingDate, LocalDate endingDate, WaitingStatus status) {
        setId(id);
        setOffer(offer);
        setUser(user);
        setAskingDate(askingDate);
        setStartingDate(startingDate);
        setEndingDate(endingDate);
        setStatus(status);
    }

    public Waiting(Offer offer, User user, LocalDate askingDate, LocalDate startingDate, LocalDate endingDate) {
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

    public LocalDate getAskingDate() {
        return askingDate;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getEndingDate() {
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

    public void setAskingDate(LocalDate askingDate) {
        this.askingDate = askingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public void setStatus(WaitingStatus status) {
        this.status = status;
    }


}
