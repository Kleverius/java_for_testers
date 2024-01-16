package tests;

import io.qameta.allure.Allure;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactInfoTests extends TestBase {

    @Test
    void TestPhonesAddressAndEmails() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData("", "My_name", "My_lastname", "My_address", "My_homephone", "", "", "", "My_email", "", "", "", ""));
        }
        var contacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(contacts.size());
        var contact = contacts.get(index);
        var phones = app.contacts().getPhones(contact);
        var address = app.contacts().getAddress(contact);
        var emails = app.contacts().getEmails(contact);
        var expectedPhones = Stream.of(contact.homePhone(), contact.mobilePhone(), contact.workPhone())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));
        var expectedAddress = Stream.of(contact.address()).collect(Collectors.joining("\n"));
        var expectedEmails = Stream.of(contact.email(), contact.email2(), contact.email3())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(expectedPhones, phones);
            Assertions.assertEquals(expectedAddress, address);
            Assertions.assertEquals(expectedEmails, emails);
        });
    }
}
