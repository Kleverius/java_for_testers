package model;

public record ContactData(
        String id,
        String firstName,
        String lastName,
        String address,
        String homePhone,
        String mobilePhone,
        String workPhone,
        String middleName,
        String email,
        String email2,
        String email3,
        String nickName,
        String company
) {

    public ContactData() {
        this("", "", "", "", "", "", "", "", "", "", "", "", "");
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.firstName, this.lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, this.middleName, this.email, this.email2, this.email3, this.nickName, this.company);
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(this.id, firstName, this.lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, this.middleName, this.email, this.email2, this.email3, this.nickName, this.company);
    }

    public ContactData withLastName(String lastName) {
        return new ContactData(this.id, this.firstName, lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, this.middleName, this.email, this.email2, this.email3, this.nickName, this.company);
    }

    public ContactData withAddress(String address) {
        return new ContactData(this.id, this.firstName, this.lastName, address, this.homePhone, this.mobilePhone, this.workPhone, this.middleName, this.email, this.email2, this.email3, this.nickName, this.company);
    }

    public ContactData withHomePhone(String homePhone) {
        return new ContactData(this.id, this.firstName, this.lastName, this.address, homePhone, this.mobilePhone, this.workPhone, this.middleName, this.email, this.email2, this.email3, this.nickName, this.company);
    }

    public ContactData withMobilePhone(String mobilePhone) {
        return new ContactData(this.id, this.firstName, this.lastName, this.address, this.homePhone, mobilePhone, this.workPhone, this.middleName, this.email, this.email2, this.email3, this.nickName, this.company);
    }

    public ContactData withWorkPhone(String workPhone) {
        return new ContactData(this.id, this.firstName, this.lastName, this.address, this.homePhone, this.mobilePhone, workPhone, this.middleName, this.email, this.email2, this.email3, this.nickName, this.company);
    }

    public ContactData withEmail(String email) {
        return new ContactData(this.id, this.firstName, this.lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, this.middleName, email, this.email2, this.email3, this.nickName, this.company);
    }

    public ContactData withEmail2(String email2) {
        return new ContactData(this.id, this.firstName, this.lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, this.middleName, this.email, email2, this.email3, this.nickName, this.company);
    }

    public ContactData withEmail3(String email3) {
        return new ContactData(this.id, this.firstName, this.lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, this.middleName, this.email, this.email2, email3, this.nickName, this.company);
    }
}
