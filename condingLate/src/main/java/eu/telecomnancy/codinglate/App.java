package eu.telecomnancy.codinglate;


import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Resource;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.ResourcesView;
import com.calendarfx.view.WeekView;
import eu.telecomnancy.codinglate.calendar.CalendarStaticContraint;
import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class App extends Application {

    private SceneManager sceneManager;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Offer offer = new OfferController().getOfferById(1);
        Calendar calendar = new CalendarStaticContraint("Bookings", offer);

        CalendarView calendarView = new CalendarView();
        calendarView.setShowDeveloperConsole(false);
        calendarView.setShowPageSwitcher(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowToolBar(false);


        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().add(calendar);
        calendarView.getCalendarSources().addAll(myCalendarSource);

        System.out.println(calendarView.getCalendars());
        Calendar defaultCalendar = calendarView.getCalendars().get(0);
        defaultCalendar.setName("CodingLate");
        defaultCalendar.setStyle(Calendar.Style.STYLE2);




        Scene scene = new Scene(calendarView);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CodingLate");
        primaryStage.show();
    }

    public static void main(String[] args) {


        launch(args);
    }
}