package eu.telecomnancy.codinglate.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Calendar.Style;
import eu.telecomnancy.codinglate.database.dataController.offer.BookingDAO;
import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.util.ArrayList;
import com.calendarfx.model.Calendar;

public class CalendarStaticContraint extends Calendar {
    public CalendarStaticContraint(String name, Offer offer) {
        super(name);
        setReadOnly(true);
        setStyle(Style.STYLE3);
        ArrayList<Booking> bookings = new BookingDAO().getBookingsByOffer(offer);
        for (Booking booking : bookings) {
            String id = String.valueOf(offer.getId());
            Entry<String> newBooking = new Entry<>(offer.getTitle(), id);
            newBooking.setInterval(booking.getStartingDate(), booking.getEndingDate());
            newBooking.setCalendar(this);
        }
    }
}
