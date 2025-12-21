package models;

import java.time.LocalDate;
import java.time.Period;


public class Person {
    protected String personId;
    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected String address;

    public Person(String personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }


    public String getFullName(){
        return firstName + " " + lastName;
    }

    public int getAge(){
        return  Period.between(dateOfBirth, LocalDate.now()).getYears();
    }



    public String getPersonId(){
        return personId;
    }



    public String getRole() {

        return getRole();
    }
}
