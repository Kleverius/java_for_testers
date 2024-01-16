package tests;

import io.qameta.allure.Allure;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData("", "My_name", "My_lastname", "My_address", "My_homephone", "", "", "", "My_email", "", "", "", ""));
        }
        var oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        app.contacts().removeContact(oldContacts.get(index));
        var newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(newContacts, expectedList);
        });
    }

    @Test
    void canRemoveAllContactsAtOnce() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData("", "My_name", "My_lastname", "My_address", "My_homephone", "", "", "", "My_email", "", "", "", ""));
        }
        app.contacts().removeAllContacts();
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(0, app.hbm().getContactCount());
        });
    }
}
