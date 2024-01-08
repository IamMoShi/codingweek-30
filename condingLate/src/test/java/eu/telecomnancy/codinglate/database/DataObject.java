package eu.telecomnancy.codinglate.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCategory;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCondition;
import eu.telecomnancy.codinglate.database.dataObject.offer.Product;
import eu.telecomnancy.codinglate.database.dataObject.offer.ProductImage;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataObject {
    @Test
    public void address() {
        // No capital letter
        Address address1 = new Address(12, "12 rue de la paix 54000 nancy");
        Address address2 = new Address("13 rue de la Guerre, 54000 nancy");

        assertEquals(12, address1.getId());
        assertEquals("12 rue de la paix 54000 nancy", address1.getAddress());
        assertEquals(-1, address2.getId());
        assertEquals("13 rue de la guerre, 54000 nancy", address2.getAddress());

        Address toLongAddress1 = new Address(10, "1".repeat(1000));

        assertEquals(10, toLongAddress1.getId());
        assertEquals(255, toLongAddress1.getAddress().length());
        assertEquals("1".repeat(255), toLongAddress1.getAddress());

        address1.setAddress("14 rue de la Guerre, 54000 nancy");
        address1.setId(25);

        assertEquals(25, address1.getId());
        assertEquals("14 rue de la guerre, 54000 nancy", address1.getAddress());
    }

    @Test
    public void user() {
        Address address = new Address(12, "12 rue de la paix 54000 Nancy");
        User user1 = new User("Jean", "Dupont", "jean.dupont@gmail.com", "password1", address);

        assertEquals(-1, user1.getId());
        assertEquals("jean", user1.getFirstname());
        assertEquals("dupont", user1.getLastname());
        assertEquals("jean.dupont@gmail.com", user1.getEmail());
        assertEquals("password1", user1.getPassword());
        assertEquals("", user1.getPhone());
        assertEquals(0, user1.getBalance());
        assertEquals(address, user1.getAddress());

        User user2 = new User(12, "John", "Doe", "john.doe@telecomnancy.eu", "password2", "03 12 34 56 78", 100, address);

        assertEquals(12, user2.getId());
        assertEquals("john", user2.getFirstname());
        assertEquals("doe", user2.getLastname());
        assertEquals("john.doe@telecomnancy.eu", user2.getEmail());
        assertEquals("password2", user2.getPassword());
        assertEquals("0312345678", user2.getPhone()); // Automatically delete spaces
        assertEquals(100, user2.getBalance());
        assertEquals(address, user2.getAddress());


    }

    @Test
    public void Offer() {
        Address address = new Address(12, "12 rue de la paix 54000 Nancy");
        User user = new User(12, "John", "Doe", "john.doe@telecomnancy.eu", "password2", "03 12 34 56 78", 100, address);
        Product product1 = new Product(user, "Product1", 10, PriceType.euro_per_day);

        assertEquals(-1, product1.getId());
        assertEquals(user, product1.getUser());
        assertEquals("product1", product1.getTitle());
        assertEquals("", product1.getDescription());
        assertEquals(10, product1.getPrice());
        assertEquals(PriceType.euro_per_day, product1.getPriceType());
        assertNull(product1.getStartingDate());
        assertNull(product1.getEndingDate());
        assertEquals(ProductCategory.Other, product1.getCategory());
        assertEquals("", product1.getBrand());
        assertEquals("", product1.getModel());
        assertEquals(0, product1.getYear());
        assertNull(product1.getCondition());
        assertEquals(0, product1.getImages().size());

        Product product2 = new Product(
                12,
                user,
                "Product2",
                "Description",
                12.2,
                PriceType.euro_per_day,
                LocalDate.of(2023, 12, 24),
                LocalDate.of(2024, 1, 1),
                ProductCategory.Other,
                "Brand",
                "Model",
                2020,
                ProductCondition.New,
                new ArrayList<>());

        assertEquals(12, product2.getId());
        assertEquals(user, product2.getUser());
        assertEquals("product2", product2.getTitle());
        assertEquals("description", product2.getDescription());
        assertEquals(12.2, product2.getPrice());
        assertEquals(PriceType.euro_per_day, product2.getPriceType());
        assertEquals(LocalDate.of(2023, 12, 24), product2.getStartingDate());
        assertEquals(LocalDate.of(2024, 1, 1), product2.getEndingDate());
        assertEquals(ProductCategory.Other, product2.getCategory());
        assertEquals("brand", product2.getBrand());
        assertEquals("model", product2.getModel());
        assertEquals(2020, product2.getYear());
        assertEquals(ProductCondition.New, product2.getCondition());
        assertEquals(0, product2.getImages().size());

        User user2 = new User("Paul", "Dupont", "paul.dupont@gmail.com", "password1", address);

        product2.setId(13);
        product2.setUser(user2);
        product2.setTitle("Product3");
        product2.setDescription("Description2");
        product2.setPrice(13.4);
        product2.setPriceType(PriceType.euro_per_hour);
        product2.setStartingDate(LocalDate.of(2023, 12, 25));
        product2.setEndingDate(LocalDate.of(2024, 1, 2));
        product2.setCategory(ProductCategory.Auto);
        product2.setBrand("Brand2");
        product2.setModel("Model2");
        product2.setYear(2021);
        product2.setCondition(ProductCondition.Used);
        product2.setImages(new ArrayList<>());

        assertEquals(13, product2.getId());
        assertEquals(user2, product2.getUser());
        assertEquals("product3", product2.getTitle());
        assertEquals("description2", product2.getDescription());
        assertEquals(13.4, product2.getPrice());
        assertEquals(PriceType.euro_per_hour, product2.getPriceType());
        assertEquals(LocalDate.of(2023, 12, 25), product2.getStartingDate());
        assertEquals(LocalDate.of(2024, 1, 2), product2.getEndingDate());
        assertEquals(ProductCategory.Auto, product2.getCategory());
        assertEquals("brand2", product2.getBrand());
        assertEquals("model2", product2.getModel());
        assertEquals(2021, product2.getYear());
        assertEquals(ProductCondition.Used, product2.getCondition());
        assertEquals(0, product2.getImages().size());


        Service service1 = new Service(
                12,
                user,
                "Service1",
                "Description",
                12.2,
                PriceType.euro_per_day,
                LocalDate.of(2023, 12, 24),
                LocalDate.of(2024, 1, 1));

        assertEquals(12, service1.getId());
        assertEquals(user, service1.getUser());
        assertEquals("service1", service1.getTitle());
        assertEquals("description", service1.getDescription());
        assertEquals(12.2, service1.getPrice());
        assertEquals(PriceType.euro_per_day, service1.getPriceType());
        assertEquals(LocalDate.of(2023, 12, 24), service1.getStartingDate());
        assertEquals(LocalDate.of(2024, 1, 1), service1.getEndingDate());

        Service service2 = new Service(user, "Service2", 10, PriceType.euro_per_week);

        assertEquals(-1, service2.getId());
        assertEquals(user, service2.getUser());
        assertEquals("service2", service2.getTitle());
        assertEquals("", service2.getDescription());
        assertEquals(10, service2.getPrice());
        assertEquals(PriceType.euro_per_week, service2.getPriceType());
        assertNull(service2.getStartingDate());
        assertNull(service2.getEndingDate());

        service2.setId(13);
        service2.setUser(user2);
        service2.setTitle("Service3");
        service2.setDescription("Description2");
        service2.setPrice(13.4);
        service2.setPriceType(PriceType.euro_per_hour);
        service2.setStartingDate(LocalDate.of(2023, 12, 25));
        service2.setEndingDate(LocalDate.of(2024, 1, 2));

        assertEquals(13, service2.getId());
        assertEquals(user2, service2.getUser());
        assertEquals("service3", service2.getTitle());
        assertEquals("description2", service2.getDescription());
        assertEquals(13.4, service2.getPrice());
        assertEquals(PriceType.euro_per_hour, service2.getPriceType());
        assertEquals(LocalDate.of(2023, 12, 25), service2.getStartingDate());
        assertEquals(LocalDate.of(2024, 1, 2), service2.getEndingDate());


    }
}
