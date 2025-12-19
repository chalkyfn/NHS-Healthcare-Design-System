package models;

import java.time.LocalDate;

public class Prescription {

    private String prescriptionId;
    private Patient patient;
    private Clinician clinician;

    private String medicationName;
    private String dosage;
    private String instructions;
    private String pharmacyName;
    private String status;
    private LocalDate issueDate;

    public Prescription(String prescriptionId,
                        Patient patient,
                        Clinician clinician,
                        String medicationName,
                        String dosage,
                        String instructions,
                        String pharmacyName,
                        String status,
                        LocalDate issueDate) {

        this.prescriptionId = prescriptionId;
        this.patient = patient;
        this.clinician = clinician;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.instructions = instructions;
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

    @Override
    public String toString() {
        return prescriptionId + " - " + medicationName +
                " (" + patient.getFullName() + ")";
    }
}
