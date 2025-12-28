package controller;

import data.DataLoader;
import data.DataSaver;
import models.*;

import java.time.LocalDate;
import java.util.List;

public class ReferralController {

    private List<Referral> referrals;


    public List<Referral> getAllReferrals(
            List<Patient> patients,
            List<Clinician> clinicians,
            List<Facility> facilities,
            List<Appointment> appointments
    ) {
        referrals = DataLoader.loadReferrals(
                "data/referrals.csv",
                patients,
                clinicians,
                facilities,
                appointments
        );
        return referrals;
    }


    public Referral findReferralById(String referralId) {
        if (referrals == null) {
            return null;
        }

        return referrals.stream()
                .filter(r -> r.getReferralId().equals(referralId))
                .findFirst()
                .orElse(null);
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

    public Appointment findAppointmentById(String id) {
        return referrals.stream()
                .map(Referral::getAppointment)
                .filter(a -> a != null && a.getAppointmentId().equals(id))
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


    public void exportReferral(Referral referral) {
        DataSaver.saveReferralText(referral);
    }

    public Referral createReferral(
            String referralId,
            String patientId,
            String referringClinicianId,
            String referredTo,
            String fromFacilityId,
            String toFacilityId,
            LocalDate referralDate,
            String urgency,
            String referralReason,
            String clinicalSummary,
            String investigations,
            String status,
            String appointmentId,
            String notes
    ) {

        Patient patient = findPatientById(patientId);
        Clinician clinician = findClinicianById(referringClinicianId);
        Clinician toClinician = findClinicianById(referredTo);
        Facility fromFacility = findFacilityById(fromFacilityId);
        Facility toFacility = findFacilityById(toFacilityId);

        Appointment appointment = null;
        if (appointmentId != null && !appointmentId.isBlank()) {
            appointment = findAppointmentById(appointmentId);
        }

        if (patient == null || clinician == null || fromFacility == null || toFacility == null) {
            throw new IllegalArgumentException("Invalid referral data");
        }

        Referral referral = new Referral(
                referralId,
                patient,
                clinician,
                toClinician,
                fromFacility,
                toFacility,
                referralDate,
                urgency,
                referralReason,
                clinicalSummary,
                investigations,
                status,
                appointment,
                notes,
                LocalDate.now(),
                LocalDate.now()
        );

        referrals.add(referral);
        return referral;
    }


}
