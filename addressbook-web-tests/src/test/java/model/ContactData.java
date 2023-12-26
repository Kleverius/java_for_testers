package model;

public record ContactData(String firstName, String lastName, String address, String homePhone, String email) {

    public ContactData() {
        this("", "", "", "", "");
    }

    public ContactData withName(String firstName) {
        return new ContactData(firstName, this.lastName, this.address, this.homePhone, this.email);
    }
}
