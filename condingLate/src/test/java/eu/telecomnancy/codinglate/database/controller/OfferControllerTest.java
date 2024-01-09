package eu.telecomnancy.codinglate.database.controller;
import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.offer.Product;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OfferControllerTest {

    @Test
    public void testInsertService() {
        PersonController userController = new PersonController();
        // Test s'il y a un utilisateur dans la base de données avec l'email john.doe@example.com
        User user = (User) userController.getPersonByEmail("john.doe@example.com");
        if (user != null) {
            userController.delete(user);
        } else {
            user = new User("John", "Doe", "john.doe@example.com", "password", new Address("1 rue du test"));
        }

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

    @Test
    public void testInsertProduct() {
        PersonController userController = new PersonController();
        // Test s'il y a un utilisateur dans la base de données avec l'email john.doe@example.com
        User user = (User) userController.getPersonByEmail("john.doe@example.com");
        if (user != null) {
            userController.delete(user);
        } else {
            user = new User("John", "Doe", "john.doe@example.com", "password", new Address("1 rue du test"));
        }

        userController.insert(user);

        // Créer un produit
        Product product = new Product(user, "Product Test", 100.0, PriceType.EURO_PER_WEEK);


        OfferController offerController = new OfferController();
        // Insérer le produit dans la base de données
        offerController.insert(product);

        // Vérifier que l'ID du produit a été attribué par la base de données
        assertNotEquals(-1, product.getId());
        assertEquals(user.getId(), product.getUser().getId());
        assertEquals("product test", product.getTitle());
    }
}
