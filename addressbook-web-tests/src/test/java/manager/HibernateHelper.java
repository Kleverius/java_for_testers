package manager;

import io.qameta.allure.Step;
import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;

public class HibernateHelper extends HelperBase {

    private SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);
        sessionFactory = new Configuration()
                .addAnnotatedClass(ContactRecord.class)
                .addAnnotatedClass(GroupRecord.class)
                .setProperty(AvailableSettings.URL, "jdbc:mysql://localhost/addressbook?zeroDateTimeBehavior=convertToNull")
                .setProperty(AvailableSettings.USER, "root")
                .setProperty(AvailableSettings.PASS, "")
                .buildSessionFactory();
    }

    static List<GroupData> convertGroupList(List<GroupRecord> groupRecords) {
        return groupRecords.stream().map(HibernateHelper::convertGroup).collect(Collectors.toList());
    }

    private static GroupData convertGroup(GroupRecord groupRecord) {
        return new GroupData("" + groupRecord.id, groupRecord.name, groupRecord.header, groupRecord.footer);
    }

    private static GroupRecord convertGroup(GroupData groupdata) {
        var id = groupdata.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id), groupdata.name(), groupdata.header(), groupdata.footer());
    }

    @Step
    public List<GroupData> getGroupList() {
        return convertGroupList(sessionFactory.fromSession(session -> {
            return session.createQuery("from GroupRecord", GroupRecord.class).list();
        }));
    }

    static List<ContactData> convertContactList(List<ContactRecord> contactRecords) {
        return contactRecords.stream().map(HibernateHelper::convertContact).collect(Collectors.toList());
    }

    private static ContactData convertContact(ContactRecord contactRecord) {
        return new ContactData(
                "" + contactRecord.id,
                contactRecord.firstname,
                contactRecord.lastname,
                contactRecord.address,
                contactRecord.home,
                contactRecord.mobile,
                contactRecord.work,
                contactRecord.middlename,
                contactRecord.email,
                contactRecord.email2,
                contactRecord.email3,
                contactRecord.nickname,
                contactRecord.company);
    }

    private static ContactRecord convertContact(ContactData contactData) {
        var id = contactData.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new ContactRecord(
                Integer.parseInt(id),
                contactData.firstName(),
                contactData.lastName(),
                contactData.address(),
                contactData.homePhone(),
                contactData.mobilePhone(),
                contactData.workPhone(),
                contactData.middleName(),
                contactData.email(),
                contactData.email2(),
                contactData.email3(),
                contactData.nickName(),
                contactData.company());
    }

    public List<ContactData> getContactList() {
        return convertContactList(sessionFactory.fromSession(session -> {
            return session.createQuery("from ContactRecord", ContactRecord.class).list();
        }));
    }

    public long getGroupCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
        });
    }

    @Step
    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convertGroup(groupData));
            session.getTransaction().commit();
        });
    }

    public long getContactCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from ContactRecord", Long.class).getSingleResult();
        });
    }

    public void createContact(ContactData contactData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convertContact(contactData));
            session.getTransaction().commit();
        });
    }

    public List<ContactData> getContactsInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            return convertContactList(session.get(GroupRecord.class, group.id()).contacts);
        });
    }
}
