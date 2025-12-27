package controller;

import data.DataLoader;
import data.DataSaver;
import models.Appointment;
import models.Clinician;
import models.Facility;
import models.Patient;

import controller.ClinicianController;

import java.util.List;

public class AppointmentController {

    public List<Appointment> getAllAppointments() {
        return data.DataLoader.loadAppointments(
                "data/appointments.csv",
                data.DataLoader.loadPatients(
                        "data/patients.csv",
                        data.DataLoader.loadFacilities("data/facilities.csv")
                ),
                data.DataLoader.loadClinicians(
                        "data/clinicians.csv",
                        data.DataLoader.loadFacilities("data/facilities.csv")
                ),
                data.DataLoader.loadFacilities("data/facilities.csv")
        );
    }

    // 🔹 THIS IS THE IMPORTANT PART
    public void addAppointment(Appointment appointment) {
        DataSaver.saveAppointment(appointment, "data/appointments.csv");
    }

    public void cancelAppointment(Appointment appointment) {
        appointment.setStatus("Cancelled");
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

    public Facility findFacilityById(String id) {
        return getAllFacilities().stream()
                .filter(f -> f.getFacilityId().equals(id))
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

    public List<Facility> getAllFacilities() {
        return DataLoader.loadFacilities("data/facilities.csv");
    }


}
