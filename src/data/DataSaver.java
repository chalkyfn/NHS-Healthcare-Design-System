package data;

import models.Prescription;
import models.Referral;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataSaver {

    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // -----------------------------------------------------
    // SAVE REFERRAL AS TEXT FILE
    // -----------------------------------------------------
    public static void saveReferralText(Referral referral, String folderPath) {

        String fileName = folderPath + "/referral_" + referral.getReferralId() + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println("==========================================");
            writer.println("           NHS REFERRAL SUMMARY");
            writer.println("==========================================");
            writer.println("Referral ID: " + referral.getReferralId());
            writer.println("Referral Date: " + referral.getReferralDate());
            writer.println();

            writer.println("---- PATIENT DETAILS ----");
            writer.println("Name: " + referral.getPatient().getFullName());
            writer.println("NHS Number: " + referral.getPatient().getNhsNumber());
            writer.println("Date of Birth: " + referral.getPatient().getDateOfBirth());
            writer.println();

            writer.println("---- REFERRING CLINICIAN ----");
            writer.println("Clinician: " + referral.getReferringClinician().getFullName());
            writer.println("Role: " + referral.getReferringClinician().getRole());
            writer.println("Facility: " +
                    (referral.getReferringFacility() != null
                            ? referral.getReferringFacility().getFacilityName()
                            : "Unknown"));
            writer.println();

            writer.println("---- REFERRED TO ----");
            writer.println("Specialist: " + referral.getReferredToClinician().getFullName());
            writer.println("Facility: " + referral.getReferredToFacility().getFacilityName());
            writer.println("Urgency: " + referral.getUrgencyLevel());
            writer.println();

            writer.println("---- CLINICAL SUMMARY ----");
            writer.println(referral.getClinicalSummary());
            writer.println();

            writer.println("---- REASON FOR REFERRAL ----");
            writer.println(referral.getReferralReason());
            writer.println();

            writer.println("---- REQUESTED INVESTIGATIONS ----");
            for (String investigation : referral.getRequestedInvestigations()) {
                writer.println("- " + investigation);
            }
            writer.println();

            writer.println("---- STATUS ----");
            writer.println("Status: " + referral.getStatus());
            writer.println("Notes: " + referral.getNotes());
            writer.println();

            writer.println("------------------------------------------");
            writer.println("Generated: " + TIMESTAMP.format(LocalDateTime.now()));
            writer.println("------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error saving referral text file.");
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------
    // SAVE PRESCRIPTION AS TEXT FILE
    // -----------------------------------------------------
    public static void savePrescriptionText(Prescription prescription, String folderPath) {

        String fileName = folderPath + "/prescription_" + prescription.getPrescriptionId() + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println("==========================================");
            writer.println("           PRESCRIPTION SUMMARY");
            writer.println("==========================================");
            writer.println("Prescription ID: " + prescription.getPrescriptionId());
            writer.println("Issue Date: " + prescription.getIssueDate());
            writer.println();

            writer.println("---- PATIENT DETAILS ----");
            writer.println("Name: " + prescription.getPatient().getFullName());
            writer.println("NHS Number: " + prescription.getPatient().getNhsNumber());
            writer.println();

            writer.println("---- ISSUING CLINICIAN ----");
            writer.println("Clinician: " + prescription.getClinician().getFullName());
            writer.println("Role: " + prescription.getClinician().getRole());
            writer.println();

            writer.println("---- MEDICATION ----");
            writer.println("Medication: " + prescription.getMedicationName());
            writer.println("Dosage: " + prescription.getDosage());
            writer.println();

            writer.println("---- COLLECTION DETAILS ----");
            writer.println("Pharmacy: " + prescription.getPharmacyName());
            writer.println("Status: " + prescription.getStatus());
            writer.println();

            writer.println("------------------------------------------");
            writer.println("Generated: " + TIMESTAMP.format(LocalDateTime.now()));
            writer.println("------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error saving prescription text file.");
            e.printStackTrace();
        }
    }
}
