package eu.telecomnancy.codinglate.database.controller;

import eu.telecomnancy.codinglate.database.dataController.offer.BookingDAO;
import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BookingDAOTest {
    @Test
    public void testInsert() {
        PersonController userController = new PersonController();
        // Test s'il y a un utilisateur dans la base de données avec l'email john.doe@example.com
        User user = (User) userController.getPersonByEmail("john.doe@example.com");
        if (user != null) {
            userController.delete(user);
        } else {
            user = new User("John", "Doe", "john.doe@example.com", "password", new Address("1 rue du test"));
        }

        userController.insert(user);

        Service service = new Service(user, "MyService", 10, PriceType.EURO_PER_WEEK);

        OfferController offerController = new OfferController();
        offerController.insert(service);

        Booking booking = new Booking(service, user, LocalDate.of(2024, 12, 2), LocalDate.of(2024, 12, 9));
        BookingDAO bookingDAO = new BookingDAO();
        bookingDAO.insert(booking);


    }

}