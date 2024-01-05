package model;

public record ContactData(String id, String firstName, String lastName, String address, String homePhone, String email, String middleName, String nickName, String company) {

    public ContactData() {
        this("", "", "", "", "", "", "", "", "");
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.firstName, this.lastName, this.address, this.homePhone, this.email, this.middleName, this.nickName, this.company);
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(this.id, firstName, this.lastName, this.address, this.homePhone, this.email, this.middleName, this.nickName, this.company);
    }

    public ContactData withLastName(String lastName) {
        return new ContactData(this.id, this.firstName, lastName, this.address, this.homePhone, this.email, this.middleName, this.nickName, this.company);
    }

    public ContactData withAddress(String address) {
        return new ContactData(this.id, this.firstName, this.lastName, address, this.homePhone, this.email, this.middleName, this.nickName, this.company);
    }

    public ContactData withHomePhone(String homePhone) {
        return new ContactData(this.id, this.firstName, this.lastName, this.address, homePhone, this.email, this.middleName, this.nickName, this.company);
    }

    public ContactData withEmail(String email) {
        return new ContactData(this.id, this.firstName, this.lastName, this.address, this.homePhone, email, this.middleName, this.nickName, this.company);
    }
}
