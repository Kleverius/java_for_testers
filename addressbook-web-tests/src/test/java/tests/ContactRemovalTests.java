package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData("My_name", "My_lastname", "My_address", "My_homephone", "My_email"));
        }
        app.contacts().removeContact();
    }
}
