package models;

public class SpecialistDoctor extends Clinician {
    public SpecialistDoctor(String id, String firstName, String lastName,
                            String qualification, Facility facility ){
        super(id, firstName, lastName, "Specialist Doctor", qualification, facility);
    }
}
