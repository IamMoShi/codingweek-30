package eu.telecomnancy.codinglate.database.controller;
import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OfferControllerTest {

    @Test
    public void testInsertService() {
        // Créer un utilisateur
        User user = new User("John", "Doe", "john.doe12@example.com", "password", new Address("123 Rue de la Test"));
        PersonController userController = new PersonController();
        userController.insert(user);

        // Créer un service
        Service service = new Service(user, "Service Test", 10.0, PriceType.EURO_PER_HOUR);

        // Insérer le service dans la base de données
        OfferController offerController = new OfferController();
        offerController.insert(service);

        // Vérifier que l'ID du service a été attribué par la base de données
        assertNotEquals(-1, service.getId());
        assertEquals(service.getUser().getId(), user.getId());
        assertEquals("service test", service.getTitle());
    }
}
