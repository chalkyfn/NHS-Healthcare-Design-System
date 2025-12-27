package controller;

import data.DataLoader;
import data.DataSaver;
import models.*;

import java.util.List;

public class PrescriptionController {

    public List<Prescription> getAllPrescriptions(
            String filePath,
            List<Patient> patients,
            List<Clinician> clinicians,
            List<Appointment> appointments
    ) {
        return DataLoader.loadPrescriptions(filePath, patients, clinicians,appointments);
    }

//    public void generatePrescriptionFile(
//            Prescription prescription,
//            String folderPath
//    ) {
//        DataSaver.savePrescriptionText(prescription, folderPath);
//    }

    public void updateStatus(Prescription prescription, String newStatus) {
        prescription.updateStatus(newStatus);
    }

    public Clinician findClinicianById(String id) {
        return getAllClinicians().stream()
                .filter(c -> c.getClinicianId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Patient findPatientById(String id) {
        return getAllPatients().stream()
                .filter(p -> p.getPatientId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Appointment findAppointmentById(String id) {
        return getAllAppointment().stream()
                .filter(f -> f.getAppointmentId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Patient> getAllPatients() {
        List<Facility> facilities =
                DataLoader.loadFacilities("data/facilities.csv");

        return DataLoader.loadPatients("data/patients.csv", facilities);
    }

    public List<Clinician> getAllClinicians() {
        List<Facility> facilities =
                DataLoader.loadFacilities("data/facilities.csv");

        return DataLoader.loadClinicians("data/clinicians.csv", facilities);
    }

    public List<Facility> getAllFacilities(){
        List<Facility> facilities =
                DataLoader.loadFacilities("data/facilities.csv");

        return facilities;

    }

    public List<Appointment> getAllAppointment() {
        return DataLoader.loadAppointments("data/appointments.csv",getAllPatients(),getAllClinicians(),getAllFacilities());
    }


}
