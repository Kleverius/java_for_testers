package manager;

import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class HibernateHelper extends HelperBase {

    private SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);
        sessionFactory = new Configuration()
                .addAnnotatedClass(ContactRecord.class)
                .addAnnotatedClass(GroupRecord.class)
                .setProperty(AvailableSettings.URL, "jdbc:mysql://localhost/addressbook")
                .setProperty(AvailableSettings.USER, "root")
                .setProperty(AvailableSettings.PASS, "")
                .buildSessionFactory();
    }

    static List<GroupData> convertGroupList(List<GroupRecord> groupRecords) {
        List<GroupData> result = new ArrayList<>();
        for (var i : groupRecords) {
            result.add(convertGroup(i));
        }
        return result;
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

    public List<GroupData> getGroupList() {
        return convertGroupList(sessionFactory.fromSession(session -> {
            return session.createQuery("from GroupRecord", GroupRecord.class).list();
        }));
    }

    static List<ContactData> convertContactList(List<ContactRecord> contactRecords) {
        List<ContactData> result = new ArrayList<>();
        for (var j : contactRecords) {
            result.add(convertContact(j));
        }
        return result;
    }

    private static ContactData convertContact(ContactRecord contactRecord) {
        return new ContactData(
                "" + contactRecord.id,
                contactRecord.firstname,
                contactRecord.lastname,
                contactRecord.address,
                contactRecord.home,
                contactRecord.email,
                contactRecord.middlename,
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
                contactData.email(),
                contactData.middleName(),
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
}
