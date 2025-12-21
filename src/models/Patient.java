package models;

import java.time.LocalDate;

public class Patient extends Person {

    private String nhsNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private LocalDate registrationDate;
    private Facility registeredFacility;


    public Patient(
            String patientId,
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String gender,
            String phone,
            String email,
            String address,
            String emergencyContactName,
            String emergencyContactPhone,
            String nhsNumber,
            String postcode,
            LocalDate registrationDate,
            Facility registeredFacility

    ) {

        super(patientId, firstName, lastName);

        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.nhsNumber = nhsNumber;
        this.registrationDate = registrationDate;
        this.registeredFacility = registeredFacility;
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
                           String emergencyContactName, String emergencyContactNumber,
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
        this.emergencyContactPhone = emergencyContactNumber;
        this.registrationDate = registrationDate;
        this.registeredFacility = facility;

    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPatientId() {
        return personId;
    }

    public String getAddress(){
        return address;
    }

    public String getPostCode(){
        return postCode;
    }


    public String getEmergencyContactName(){
        return emergencyContactName;
    }
    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public LocalDate getRegistrationDate(){
        return registrationDate;
    }








}