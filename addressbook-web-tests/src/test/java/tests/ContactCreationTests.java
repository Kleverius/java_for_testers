package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() {
        var result = new ArrayList<ContactData>();
        for (var firstName : List.of("", "My_name")) {
            for (var lastName : List.of("", "My_lastname")) {
                for (var address : List.of("", "My_address")) {
                    for (var homePhone : List.of("", "My_homePhone")) {
                        for (var email : List.of("", "My_email")) {
                            result.add(new ContactData()
                                    .withFirstName(firstName)
                                    .withLastName(lastName)
                                    .withAddress(address)
                                    .withHomePhone(homePhone)
                                    .withEmail(email));
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstName(randomString(i * 10))
                    .withLastName(randomString(i * 10))
                    .withAddress(randomString(i * 10))
                    .withHomePhone(randomString(i * 10))
                    .withEmail(randomString(i * 10)));
        }
        return result;
    }

    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateMultipleContacts(ContactData contact) {
        var oldContacts = app.contacts().getList();
        app.contacts().createContact(contact);
        var newContacts = app.contacts().getList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact
                .withId(newContacts.get(newContacts.size() - 1).id())
                .withAddress("")
                .withHomePhone("")
                .withEmail(""));
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    public static List<ContactData> negativeContactProvider() {
        var result = new ArrayList<ContactData>(List.of(
                new ContactData("", "My_name'", "", "", "", "")));
        return result;
    }

    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void canNotCreateContact(ContactData contact) {
        var oldContacts = app.contacts().getList();
        app.contacts().createContact(contact);
        var newContacts = app.contacts().getList();
        Assertions.assertEquals(newContacts, oldContacts);
    }
}
