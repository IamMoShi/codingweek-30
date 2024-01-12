package eu.telecomnancy.codinglate.database.controller;

import eu.telecomnancy.codinglate.database.dataController.user.AddressDAO;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {
    private final AddressDAO addressController = new AddressDAO();

    @Test
    public void testInsertAddress() {
        Address newAddress = new Address("123 Rue de la Test");
        addressController.insert(newAddress);

        int addressId = newAddress.getId();
        Address retrievedAddress = addressController.getAddressById(addressId);

        assertNotNull(retrievedAddress);
        assertEquals("123 rue de la test", retrievedAddress.getAddress());
    }

    @Test
    public void testUpdateAddress() {
        Address newAddress = new Address("456 Avenue de Test");
        addressController.insert(newAddress);

        int addressId = newAddress.getId();
        Address retrievedAddress = addressController.getAddressById(addressId);

        assertNotNull(retrievedAddress);

        retrievedAddress.setAddress("789 Boulevard de Test");
        addressController.update(retrievedAddress);

        Address updatedAddress = addressController.getAddressById(addressId);
        assertEquals("789 boulevard de test", updatedAddress.getAddress());
    }

    @Test
    public void testDeleteAddress() {
        Address newAddress = new Address("987 Avenue de Test");
        addressController.insert(newAddress);

        int addressId = newAddress.getId();
        Address retrievedAddress = addressController.getAddressById(addressId);

        assertNotNull(retrievedAddress);

        addressController.deleteAddressById(addressId);
        Address deletedAddress = addressController.getAddressById(addressId);

        assertNull(deletedAddress);
    }
}
