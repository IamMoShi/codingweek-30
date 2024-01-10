package eu.telecomnancy.codinglate.database.dataObject.offer;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;
import eu.telecomnancy.codinglate.database.dataObject.enums.BookingStatus;
import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.time.LocalDateTime;

public class Booking extends DataObject {
    private int id;
    private Offer offer;
    private User user;
    private LocalDateTime startingDate;
    private LocalDateTime endingDate;
    private BookingStatus status;

    // CONSTRUCTORS -------------------------------------------------------------------------------

    public Booking(int id, Offer offer, User user, LocalDateTime startingDate, LocalDateTime endingDate, BookingStatus status) {
        setId(id);
        setOffer(offer);
        setUser(user);
        setStartingDate(startingDate);
        setEndingDate(endingDate);
        setStatus(status);
    }

    public Booking(Offer offer, User user, LocalDateTime startingDate, LocalDateTime endingDate) {
        this(-1, offer, user, startingDate, endingDate, BookingStatus.PENDING);
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

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    public BookingStatus getStatus() {
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

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }


}
