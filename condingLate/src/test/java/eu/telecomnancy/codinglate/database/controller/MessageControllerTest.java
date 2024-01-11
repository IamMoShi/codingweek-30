package eu.telecomnancy.codinglate.database.controller;

import eu.telecomnancy.codinglate.database.dataController.MessageController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import org.junit.jupiter.api.Test;

public class MessageControllerTest {
    @Test
    public void testGetConversationList() {
        Person person = PersonController.getInstance().getPersonByEmail("neyenselise@gmail.com");
        System.out.println(new MessageController().getConversationList(person));
    }
}
