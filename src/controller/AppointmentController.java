package controller;

import models.Appointment;
import models.Patient;
import models.Clinician;
import models.Facility;

import data.DataLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentController {

    public List<Appointment> getAllAppointments() {

        List<Facility> facilities =
                DataLoader.loadFacilities("data/facilities.csv");

        List<Patient> patients =
                DataLoader.loadPatients("data/patients.csv", facilities);

        List<Clinician> clinicians =
                DataLoader.loadClinicians("data/clinicians.csv", facilities);

        return DataLoader.loadAppointments(
                "data/appointments.csv",
                patients,
                clinicians,
                facilities
        );
    }



    public Appointment createAppointment(
            String appointmentId,
            Patient patient,
            Clinician clinician,
            Facility facility,
            LocalDate date,
            LocalTime time,
            int duration,
            String type,
            String reason) {

        return new Appointment(
                appointmentId,
                patient,
                clinician,
                facility,
                date,
                time,
                duration,
                type,
                "Scheduled",
                reason,
                "",
                LocalDate.now(),
                LocalDate.now()
        );
    }

    public void cancelAppointment(Appointment appointment) {
        appointment.setStatus("Cancelled");
    }
}
