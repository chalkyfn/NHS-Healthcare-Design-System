package models;

import java.time.LocalDate;

public class Patient extends Person {

    private String nhsNumber;
    private String emergencyContactName;
    private String getEmergencyContactNumber;
    private LocalDate registrationDate;
    private Facility registeredFacility;


    public Patient(String patientId, String firstName, String lastName,
                   LocalDate dob, String nhsNumber, String gender,
                   String phone, String email, String address, String postcode,
                   String emergencyContactName, String getEmergencyContactNumber,
                   LocalDate registrationDate, Facility facility) {

        this.personId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dob;
        this.gender = gender;
        this.nhsNumber = nhsNumber;
        this.phoneNumber = phone;
        this.email = email;
        this.address = address;
        this.postCode = postcode;
        this.emergencyContactName = emergencyContactName;
        this.getEmergencyContactNumber = getEmergencyContactNumber;
        this.registrationDate = registrationDate;
        this.registeredFacility = facility;
    }

    public String toString() {
        return getFullName() + " (" + nhsNumber + ")";
    }

    public String getNhsNumber(){
        return nhsNumber;
    }

    public Facility getRegisteredFacility(){
        return registeredFacility;
        }

    public void updateInfo(String patientId, String firstName, String lastName,
                           LocalDate dob, String nhsNumber, String gender,
                           String phone, String email, String address, String postcode,
                           String emergencyContactName, String getEmergencyContactNumber,
                           LocalDate registrationDate, Facility facility){

        this.personId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dob;
        this.gender = gender;
        this.nhsNumber = nhsNumber;
        this.phoneNumber = phone;
        this.email = email;
        this.address = address;
        this.postCode = postcode;
        this.emergencyContactName = emergencyContactName;
        this.getEmergencyContactNumber = getEmergencyContactNumber;
        this.registrationDate = registrationDate;
        this.registeredFacility = facility;

    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}