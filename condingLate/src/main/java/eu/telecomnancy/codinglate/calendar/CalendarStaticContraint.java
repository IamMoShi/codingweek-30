package eu.telecomnancy.codinglate.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Calendar.Style;
import eu.telecomnancy.codinglate.database.dataController.offer.BookingDAO;
import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import com.calendarfx.model.Calendar;

public class CalendarStaticContraint extends Calendar {
    public CalendarStaticContraint(String name, Offer offer) {
        super(name);
        setReadOnly(true);
        setStyle(Style.STYLE1);
        ArrayList<Booking> bookings = new BookingDAO().getBookingsByOffer(offer);
        for (Booking booking : bookings) {
            String id = String.valueOf(booking.getId());
            Entry<String> newBooking = new Entry<>(offer.getTitle(), id);
            newBooking.setInterval(booking.getStartingDate(), booking.getEndingDate());
            newBooking.setCalendar(this);
        }

        if (offer.getStartingDate() != null) {
            // Bloquer les périodes de réservation qui ne sont pas entre le début de l'offre et la fin de l'offre
            Entry<String> beforeAvailable = new Entry<>("L'offre n'est pas encore disponible", "before");
            beforeAvailable.setInterval(LocalDateTime.of(1000, 1, 1, 0, 0),offer.getStartingDate());
            beforeAvailable.setCalendar(this);
        }

        if (offer.getEndingDate() != null) {
            Entry<String> afterAvailable = new Entry<>("L'offre n'est plus disponible", "after");
            afterAvailable.setInterval(offer.getEndingDate(), LocalDateTime.of(3000, 1, 1, 0, 0));
            afterAvailable.setCalendar(this);
        }

    }
}
