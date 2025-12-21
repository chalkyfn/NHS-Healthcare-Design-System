package models;

import java.time.LocalDate;

public class Prescription {

    private String prescriptionId;
    private Patient patient;
    private Clinician clinician;
    private Appointment appointment;

    private LocalDate prescription_date;

    private String medicationName;
    private String dosage;
    private String instructions;
    private String quantity;
    private String frequency;
    private int duration_days;

    private String pharmacyName;
    private String status;

    private LocalDate issueDate;
    private LocalDate collectionDate;

    // -------------------------------------------------
    // CONSTRUCTOR (matches prescriptions.csv exactly)
    // -------------------------------------------------
    public Prescription(
            String prescriptionId,
            Patient patientId,
            Clinician clinicianId,
            Appointment appointmentId,
            LocalDate prescription_date,
            String medicationName,
            String dosage,
            String frequency,
            int duration_days,
            String quantity,
            String instructions,
            String pharmacyName,
            String status,
            LocalDate issueDate

    ) {
        this.prescriptionId = prescriptionId;
        this.patient = patientId;
        this.clinician = clinicianId;
        this.appointment = appointmentId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.prescription_date = prescription_date;
        this.duration_days = duration_days;
        this.instructions = instructions;
        this.quantity = quantity;
        this.frequency = frequency;
        this.pharmacyName = pharmacyName;
        this.status = status;
        this.issueDate = issueDate;

    }

    // ---------------- GETTERS ----------------

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Clinician getClinician() {
        return clinician;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getQuantity() {
        return quantity;
    }


    public int getDurationDays() {
        return duration_days;
    }

    public LocalDate getCollectionDate() {
        return collectionDate;
    }

    public String getAppointmentId() {
        return appointment != null ? appointment.getAppointmentId() : "null";
    }

    public LocalDate getPrescriptionDate() {
        return prescription_date;
    }
}
