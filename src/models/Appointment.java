package models;

import javax.xml.validation.SchemaFactoryLoader;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String appointmentId;

    private Patient patient;
    private Clinician clinician;
    private Facility facility;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    private int duration;
    private String appointmentType;
    private String status;
    private String reasonForVisit;
    private String notes;
    private LocalDate createdDate;
    private LocalDate lastModified;

    public  Appointment(String appointmentId, Patient patient, Clinician clinician,
                        Facility facility, LocalDate date, LocalTime time,
                        int durationMinutes, String type, String status,
                        String reason, String notes, LocalDate created, LocalDate modified){

        this.appointmentId = appointmentId;
        this.patient = patient;
        this.clinician = clinician;
        this.facility = facility;
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.duration = durationMinutes;
        this.appointmentType = type;
        this.status = status;
        this.reasonForVisit = reason;
        this.notes = notes;
        this.createdDate = created;
        this.lastModified = modified;
    }
    public void schedule_appointment(){
        status = "Scheduled";
    }

    public void cancel_appointment(){
        status = "cancelled";
        lastModified = LocalDate.now();
    }

    public void modifyAppointment(LocalDate appointmentDate, LocalTime appointmentTime){
            this.appointmentDate = appointmentDate;
            this.appointmentTime = appointmentTime;
            lastModified = LocalDate.now();

    }

}
