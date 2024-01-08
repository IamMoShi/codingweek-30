package eu.telecomnancy.codinglate.database.dataController.user;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataObject.user.Admin;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonController {

    private static PersonController instance = null;

    public static PersonController getInstance() {
        if (instance == null) {
            instance = new PersonController();
        }
        return instance;
    }

    public void insert(Person person) {
        Connection conn = DbConnection.connect();
        PreparedStatement pstmt;
        try {
            String sql = "INSERT INTO user(firstname, lastname, email, password, phone, balance, admin) VALUES(?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, person.getFirstname());
            pstmt.setString(2, person.getLastname());
            pstmt.setString(3, person.getEmail());
            pstmt.setString(4, person.getPassword());
            pstmt.setString(5, person.getPhone());
            pstmt.setFloat(6, person.getBalance());
            if (person instanceof Admin) {
                pstmt.setInt(7, 1);
            } else {
                pstmt.setInt(7, 0);
            }
            pstmt.execute();
            System.out.println("Person inserted");
        } catch (SQLException e) {
            System.out.println(e+"");
        }
    }



}
