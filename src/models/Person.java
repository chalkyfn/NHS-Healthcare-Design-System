package models;

import java.time.LocalDate;
import java.time.Period;


public class Person {
    protected String personId;
    protected String firstName;
    protected String lastName;
    protected String gender;
    protected LocalDate dateOfBirth;
    protected String phoneNumber;
    protected String email;
    protected String address;
    protected String postCode;

    public Person(String personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public int getAge(){
        return  Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public void updateContactInfo(String phoneNumber, String email){
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getPersonId(){
        return personId;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return getRole();
    }
}
