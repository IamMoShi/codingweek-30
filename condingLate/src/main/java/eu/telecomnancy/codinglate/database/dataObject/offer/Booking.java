package eu.telecomnancy.codinglate.database.dataObject.offer;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;
import eu.telecomnancy.codinglate.database.dataObject.enums.BookingStatus;
import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.time.LocalDate;

public class Booking extends DataObject {
    private int id;
    private Offer offer;
    private User user;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private BookingStatus status;

    // CONSTRUCTORS -------------------------------------------------------------------------------

    public Booking(int id, Offer offer, User user, LocalDate startingDate, LocalDate endingDate, BookingStatus status) {
        setId(id);
        setOffer(offer);
        setUser(user);
        setStartingDate(startingDate);
        setEndingDate(endingDate);
        setStatus(status);
    }

    public Booking(Offer offer, User user, LocalDate startingDate, LocalDate endingDate) {
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

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getEndingDate() {
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

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }


}
