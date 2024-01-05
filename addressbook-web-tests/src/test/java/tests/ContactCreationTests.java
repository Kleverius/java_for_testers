package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonFunctions;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<ContactData>();
//        for (var firstName : List.of("", "My_name")) {
//            for (var lastName : List.of("", "My_lastname")) {
//                for (var address : List.of("", "My_address")) {
//                    for (var homePhone : List.of("", "My_homePhone")) {
//                        for (var email : List.of("", "My_email")) {
//                            result.add(new ContactData()
//                                    .withFirstName(firstName)
//                                    .withLastName(lastName)
//                                    .withAddress(address)
//                                    .withHomePhone(homePhone)
//                                    .withEmail(email));
//                        }
//                    }
//                }
//            }
//        }
        var json = "";
        try (var reader = new FileReader("contacts.json");
             var breader = new BufferedReader(reader)
        ) {
            var line = breader.readLine();
            while (line != null) {
                json = json + line;
                line = breader.readLine();
            }
        }
        // var json = Files.readString(Paths.get("contacts.json"));
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(json, new TypeReference<List<ContactData>>() {});
        result.addAll(value);
        return result;
    }

    public static List<ContactData> singleRandomContact() {
        return List.of(new ContactData()
                .withFirstName(CommonFunctions.randomString(5))
                .withLastName(CommonFunctions.randomString(6))
                .withAddress(CommonFunctions.randomString(7))
                .withHomePhone(CommonFunctions.randomString(8))
                .withEmail(CommonFunctions.randomString(9)));
    }

    @ParameterizedTest
    @MethodSource("singleRandomContact")
    public void canCreateContact(ContactData contact) {
        var oldContacts = app.hbm().getContactList();
        app.contacts().createContact(contact);
        var newContacts = app.hbm().getContactList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        var maxId = newContacts.get(newContacts.size() - 1).id();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(maxId));
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    public static List<ContactData> negativeContactProvider() {
        var result = new ArrayList<ContactData>(List.of(
                new ContactData("", "My_name'", "", "", "", "","", "", "")));
        return result;
    }

    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void canNotCreateContact(ContactData contact) {
        var oldContacts = app.hbm().getContactList();
        app.contacts().createContact(contact);
        var newContacts = app.hbm().getContactList();
        Assertions.assertEquals(newContacts, oldContacts);
    }
}
