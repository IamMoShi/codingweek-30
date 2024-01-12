package eu.telecomnancy.codinglate.database.controller;

import eu.telecomnancy.codinglate.database.dataController.MessageDAO;
import eu.telecomnancy.codinglate.database.dataController.user.PersonDAO;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import org.junit.jupiter.api.Test;

public class MessageControllerTest {
    @Test
    public void testGetConversationList() {
        Person person = PersonDAO.getInstance().getPersonByEmail("neyenselise@gmail.com");
        System.out.println(new MessageDAO().getConversationList(person));
    }
}
