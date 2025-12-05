package models;

public class GP extends Clinician {
    public GP (String id, String firstName, String lastName, String qualification, Facility facility){
        super(id,firstName,lastName,"GP",qualification,facility);
    }
}
