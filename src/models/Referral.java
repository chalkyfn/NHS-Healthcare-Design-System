package models;

import java.time.LocalDate;
import java.util.List;
import models.Patient;
import models.Appointment;


public class Referral {

    private String referralId;
    private Patient patient;
    private Clinician referringClinician;
    private Clinician referredToClinician;
    private Facility referringFacility;
    private Facility referredToFacility;
    private LocalDate referralDate;
    private String urgencyLevel;
    private String referralReason;
    private String clinicalSummary;
    private String requestedInvestigations;
    private String status;
    private String notes;
    private LocalDate createdDate;
    private LocalDate lastUpdated;
    private Appointment appointment;

    public Referral(
            String referralId,
            Patient patient,
            Clinician referringClinician,
            Clinician referredToClinician,
            Facility referringFacility,
            Facility referredToFacility,
            LocalDate referralDate,
            String urgencyLevel,
            String referralReason,
            String clinicalSummary,
            String requestedInvestigations,
            String status,
            Appointment appointment,
            String notes,
            LocalDate createdDate,
            LocalDate lastUpdated
    ) {
        this.referralId = referralId;
        this.patient = patient;
        this.referringClinician = referringClinician;
        this.referredToClinician = referredToClinician;
        this.referringFacility = referringFacility;
        this.referredToFacility = referredToFacility;
        this.referralDate = referralDate;
        this.urgencyLevel = urgencyLevel;
        this.referralReason = referralReason;
        this.clinicalSummary = clinicalSummary;
        this.requestedInvestigations = requestedInvestigations;
        this.status = status;
        this.appointment = appointment;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }
// Getters

    public String getReferralId() {
        return referralId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Clinician getReferringClinician() {
        return referringClinician;
    }

    public Clinician getReferredToClinician() {
        return referredToClinician;
    }

    public Facility getReferringFacility() {
        return referringFacility;
    }

    public Facility getReferredToFacility() {
        return referredToFacility;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.lastUpdated = LocalDate.now();
    }

    public LocalDate getReferralDate() {
        return referralDate;
    }

    public String getClinicalSummary() {
        return clinicalSummary;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public String getRequestedInvestigations() {
        return requestedInvestigations;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getCreatedDate(){
        return createdDate;
    }

    public LocalDate getLastUpdated(){
        return  lastUpdated;
    }

    public Appointment getAppointment(){
        return appointment;
    }


}
