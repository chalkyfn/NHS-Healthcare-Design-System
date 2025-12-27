package models;

public abstract class Clinician extends Person {

    private String clinicianID;
    private String role;
    private String qualification;
    private Facility facillity;

    public Clinician(String clinicianID, String firstName, String lastName,
                     String role, String qualification, Facility facility){
        super(clinicianID,firstName,lastName);
        this.clinicianID = clinicianID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.qualification = qualification;
        this.facillity = facility;
    }


    public String getClinicianId(){
        return clinicianID;
    }

    public Facility getFacillity(){
        return facillity;
    }

    public String toString(){
        return getFullName() + "(" + role + ")" ;
    }

    public String getRole() {
        return role;
    }
}
