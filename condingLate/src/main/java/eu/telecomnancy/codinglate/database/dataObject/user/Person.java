package eu.telecomnancy.codinglate.database.dataObject.user;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;
import eu.telecomnancy.codinglate.database.dataObject.StringControlled;


public abstract class Person extends DataObject {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private float balance;
    private Address address;

    public Person(int id, String firstname, String lastname, String email, String password, String phone, float balance, Address address) {
        setId(id);
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
        setBalance(balance);
        setAddress(address);
    }


    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstname + '\'' +
                ", lastName='" + lastname + '\'' +
                ", email='" + email + '\'';
    }

    // GETTERS -----------------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public float getBalance() {
        return balance;
    }

    public Address getAddress() {
        return address;
    }

    // SETTERS -----------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = StringControlled.correctedString(firstname, 30);
    }

    public void setLastname(String lastname) {
        this.lastname = StringControlled.correctedString(lastname, 30);
    }

    public void setEmail(String email) {
        this.email = StringControlled.correctedString(email, 60);
    }

    public void setPassword(String password) {
        this.password = StringControlled.correctedString(password, 300);
    }

    public void setPhone(String phone) {
        this.phone = StringControlled.correctedString(phone, 15).replaceAll(" ", "");
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
