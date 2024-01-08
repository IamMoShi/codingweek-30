package eu.telecomnancy.codinglate.database.dataObject.user;

public class User extends Person {
    public User(String firstname, String lastname, String email, String password, Address address) {
        super(-1, firstname, lastname, email, password, "", 0, address);
    }

    public User(int id, String firstname, String lastname, String email, String password, String phone, float balance, Address address) {
        super(id, firstname, lastname, email, password, phone, balance, address);
    }
}
