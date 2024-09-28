package baanhemsieunhan.librarymanagement.data;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;

    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void getInfo() {
        System.out.println("ID: " + personID);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
    }

}
