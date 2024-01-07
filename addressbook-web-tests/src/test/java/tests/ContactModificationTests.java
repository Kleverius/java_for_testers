package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactModificationTests extends TestBase {

    @Test
    void canModifyContact() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData("", "My_name", "My_lastname", "My_address", "My_homephone", "My_email", "", "", ""));
        }
        var oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var testData = new ContactData().withFirstName("modified first name");
        app.contacts().modifyContact(oldContacts.get(index), testData);
        var newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.set(index, testData.withId(oldContacts.get(index).id()));
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @Test
    void canAddContactToGroup() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData("", "My_name", "My_lastname", "My_address", "My_homephone", "My_email", "", "", ""));
        }
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "testgroup name", "testgroup header", "testgroup footer"));
        }
        contactAndGroup result = findContactAndGroup();
        if (result.targetContact().size() == 0) {
            app.hbm().createGroup(new GroupData("", "testgroup name", "testgroup header", "testgroup footer"));
            result = findContactAndGroup();
        }
        var contactToAdd = result.targetContact().get(0);
        var groupToAdd = result.targetGroup().get(0);
        var oldRelated = app.hbm().getContactsInGroup(groupToAdd);
        app.contacts().addContactToGroup(contactToAdd, groupToAdd);
        var newRelated = app.hbm().getContactsInGroup(groupToAdd);
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newRelated.sort(compareById);
        var expectedList = new ArrayList<>(oldRelated);
        expectedList.add(contactToAdd);
        expectedList.sort(compareById);
        Assertions.assertEquals(newRelated, expectedList);
    }

    private static contactAndGroup findContactAndGroup() {
        var contactList = app.hbm().getContactList();
        var groupList = app.hbm().getGroupList();
        var targetContact = new ArrayList<ContactData>();
        var targetGroup = new ArrayList<GroupData>();
        for (var contact : contactList) {
            for (var group : groupList) {
                var contactsInGroup = app.hbm().getContactsInGroup(group);
                if (contactsInGroup.size() == 0) {
                    targetContact.add(contact);
                    targetGroup.add(group);
                } else {
                    var result = 0;
                    for (var index : contactsInGroup) {
                        if (index.equals(contact)) {
                            result = result + 1;
                        }
                    }
                    if (result == 0) {
                        targetContact.add(contact);
                        targetGroup.add(group);
                    }
                }
            }
        }
        contactAndGroup result = new contactAndGroup(targetContact, targetGroup);
        return result;
    }

    private record contactAndGroup(ArrayList<ContactData> targetContact, ArrayList<GroupData> targetGroup) {
    }

    @Test
    void canRemoveContactFromGroup() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData("", "My_name", "My_lastname", "My_address", "My_homephone", "My_email", "", "", ""));
        }
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "testgroup name", "testgroup header", "testgroup footer"));
        }
        var group = app.hbm().getGroupList().get(0);
        var contactsInGroup = app.hbm().getContactsInGroup(group);
        if (contactsInGroup.size() == 0) {
            var contact = app.hbm().getContactList().get(0);
            app.contacts().addContactToGroup(contact, group);
        }
        var oldRelated = app.hbm().getContactsInGroup(group);
        var contactToRemove = oldRelated.get(0);
        app.contacts().removeContactFromGroup(contactToRemove, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newRelated.sort(compareById);
        var expectedList = new ArrayList<>(oldRelated);
        expectedList.remove(contactToRemove);
        Assertions.assertEquals(newRelated, expectedList);
    }
}
