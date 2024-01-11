package eu.telecomnancy.codinglate.calendar;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Resource;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.ResourcesView;
import com.calendarfx.view.WeekView;

import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationCalendarView extends CalendarView {

    private Calendar defaultCalendar;
    private Offer offer;

    public ReservationCalendarView(Offer offer) {

        this.offer = offer;

        this.setShowDeveloperConsole(false);
        this.setShowPageSwitcher(false);
        this.setShowSearchField(false);
        this.setShowAddCalendarButton(false);
        this.setShowPageToolBarControls(false);
        this.setShowPrintButton(false);
        this.setShowToolBar(false);

        Calendar calendar = new CalendarStaticContraint("Bookings", offer);
        calendar.setStyle(Calendar.Style.STYLE1);


        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().add(calendar);


        this.getCalendarSources().addAll(myCalendarSource);
        System.out.println("Nb de calendrier = " + this.getCalendars().size());

        defaultCalendar = this.getCalendars().get(0);

    }

    public Booking getBooking() {
        List<Entry> entries = defaultCalendar.findEntries("");
        // Regarde s'il y a bien une et une seule entrée dans le calendrier
        if (entries.size() == 1) {
            Entry entry = entries.get(0);
            // Regarde si l'entrée est bien une réservation

            LocalDateTime startingDate = entry.getStartAsLocalDateTime();
            LocalDateTime endingDate = entry.getEndAsLocalDateTime();
            return new Booking(offer, (User) PersonController.getInstance().getCurrentUser(), startingDate, endingDate);

        }
        return null;
    }
}
