package eu.telecomnancy.codinglate.database.controller;

import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.offer.RatingDAO;
import eu.telecomnancy.codinglate.database.dataController.user.PersonDAO;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.offer.Rating;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RatingDAOTest {
    @Test
    public void testInsert() {
        PersonDAO userController = new PersonDAO();
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

        Rating rating = new Rating(service, user, 5);
        RatingDAO ratingDAO = new RatingDAO();

        ratingDAO.insert(rating);

        assertNotEquals(-1, rating.getId());
    }

}
