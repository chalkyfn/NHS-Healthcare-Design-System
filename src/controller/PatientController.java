package controller;

import models.Patient;
import models.Facility;
import data.DataLoader;
import data.DataSaver;

import java.util.List;

public class PatientController {

    public List<Patient> getAllPatients() {

        List<Facility> facilities =
                DataLoader.loadFacilities("data/facilities.csv");

        return DataLoader.loadPatients(
                "data/patients.csv",
                facilities
        );
    }

    public Patient getPatientById(String patientId) {
        for (Patient p : getAllPatients()) {
            if (p.getPatientId().equals(patientId)) {
                return p;
            }
        }
        return null;
    }

    public void addPatient(Patient patient) {
        DataSaver.savePatient(patient, "data/patients.csv");
    }

}
