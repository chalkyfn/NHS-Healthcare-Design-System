package controller;

import models.Clinician;
import models.Facility;
import data.DataLoader;

import java.util.List;

public class ClinicianController {

    public List<Clinician> getAllClinicians() {

        List<Facility> facilities =
                DataLoader.loadFacilities("data/facilities.csv");

        return DataLoader.loadClinicians(
                "data/clinicians.csv",
                facilities
        );
    }

    public Clinician getClinicianById(String clinicianId) {
        for (Clinician c : getAllClinicians()) {
            if (c.getClinicianId().equals(clinicianId)) {
                return c;
            }
        }
        return null;
    }
}
