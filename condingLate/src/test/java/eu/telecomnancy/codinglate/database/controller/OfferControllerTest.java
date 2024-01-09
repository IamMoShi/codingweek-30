package eu.telecomnancy.codinglate.database.controller;
import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCondition;
import eu.telecomnancy.codinglate.database.dataObject.offer.Product;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCategory;

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

    @Test
    public void testUpdateService() {
        /*
         * ATTENTION IL FAUT REGARDER L'AJOUT DANS LA BASE DE DONNÉES POUR COMPRENDRE CE QUI SE PASSE
         */

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

        // Mettre à jour le service --------------------


        service.setTitle("Service Test Updated");
        service.setDescription("Description updated");
        service.setPrice(20.0);
        service.setPriceType(PriceType.EURO_PER_DAY);

        offerController.update(service);

    }
    @Test
    public void testUpdateProduct() {
        /*
         * ATTENTION IL FAUT REGARDER L'AJOUT DANS LA BASE DE DONNÉES POUR COMPRENDRE CE QUI SE PASSE
         */

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

        // Insérer le produit dans la base de données
        OfferController offerController = new OfferController();
        offerController.insert(product);

        // Vérifier que l'ID du produit a été attribué par la base de données
        assertNotEquals(-1, product.getId());
        assertEquals(user.getId(), product.getUser().getId());
        assertEquals("product test", product.getTitle());

        // Mettre à jour le produit --------------------

        product.setTitle("Product Test Updated");
        product.setDescription("Description updated");
        product.setPrice(200.0);
        product.setPriceType(PriceType.EURO_PER_DAY);
        product.setCategory(ProductCategory.OTHER); // Category updated
        product.setBrand("Brand Updated");
        product.setModel("Model Updated");
        product.setCondition(ProductCondition.NEW); // Condition updated
        product.setYear(2023); // Year updated

        offerController.update(product);
    }

}
