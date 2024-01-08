package eu.telecomnancy.codinglate.database.dataObject.user;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;
import eu.telecomnancy.codinglate.database.dataObject.StringControlled;


public class Address extends DataObject {
    private int id;
    private String address;

    public Address(int id, String address) {
        this.id = id;
        this.address = StringControlled.correctedString(address, 255);
    }

    public Address(String address) {
        this(-1, address);
    }

    // Getters ------------------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    // Setters ------------------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
