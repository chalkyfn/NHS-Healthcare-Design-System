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
        return DataLoader.loadAppointments();
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
