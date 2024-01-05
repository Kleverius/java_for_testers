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

    public List<ContactData> getContactList() {
        return convertContactList(sessionFactory.fromSession(session -> {
            return session.createQuery("from ContactRecord", ContactRecord.class).list();
        }));
    }
}
