package models;

public class Nurse extends Clinician {
    public Nurse(String id, String firstName, String lastName,
                 String qualification, Facility facility){
        super(id,firstName, lastName,"Nurse",qualification,facility);
    }
}
