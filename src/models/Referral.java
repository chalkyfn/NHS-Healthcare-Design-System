package models;

import java.time.LocalDate;

public class Referral {

    private String referralId;
    private Patient patient;
    private Clinician referringClinician;
    private Clinician referringToClinician;
    private Facility referringFacility;
    private Facility referringToFacility;
    private LocalDate referralDate;
    private String urgencyLevel;
    private String referralReason;
    private String clinicalSummary;
    private String[] requestedInvestigation;
    private String status;
    private String notes;
    private LocalDate createdDate;
    private LocalDate lastUpdated;
    private Appointment linkedAppointment;


    public String getReferralId() {
        return referralId;
    }

    public void updateStatus(String newStatus){
            this.status = newStatus;
            this.lastUpdated = LocalDate.now();
    }

    public String generateReferralFileText(){
        return referralId + "," + patient.getFullName() + "," + referralReason;
    }


    public LocalDate getReferralDate() {
        return referralDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public Facility getReferringFacility() {
        return referringFacility;
    }

    public Person getReferringClinician() {
        return referringClinician;
    }

    public Person getReferredToClinician() {
        return referringToClinician;
    }

    public Facility getReferredToFacility() {
        return referringToFacility;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public String getClinicalSummary() {
        return clinicalSummary;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public String[] getRequestedInvestigations() {
        return requestedInvestigation;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }
}
