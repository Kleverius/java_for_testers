package manager.hbm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addressbook")
public class ContactRecord {
    @Id
    public int id;
    public String firstname;
    public String middlename;
    public String lastname;
    public String nickname;
    public String company;
    public String address;
    public String home;
    public String email;
}
