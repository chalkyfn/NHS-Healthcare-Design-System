package data;

import models.Prescription;
import models.Referral;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataSaver {

    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // -----------------------------------------------------
    // SAVE A REFERRAL AS A TEXT FILE
    // -----------------------------------------------------
    public static void saveReferralText(Referral referral, String folderPath) {

        String fileName = folderPath + "/referral_" + referral.getReferralId() + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println("==========================================");
            writer.println("        NHS REFERRAL SUMMARY");
            writer.println("==========================================");
            writer.println("Referral ID: " + referral.getReferralId());
            writer.println("Date Created: " + referral.getReferralDate());
            writer.println();

            writer.println("---- PATIENT DETAILS ----");
            writer.println("Name: " + referral.getPatient().getFullName());
            writer.println("NHS Number: " + referral.getPatient().getNhsNumber());
            writer.println("DOB: " + referral.getPatient().getDateOfBirth());
            writer.println("Registered GP: " +
                    (referral.getReferringFacility() != null
                            ? referral.getReferringFacility().getFacilityName()
                            : "Unknown"));
            writer.println();

            writer.println("---- REFERRING CLINICIAN ----");
            writer.println("Clinician: " + referral.getReferringClinician().getFullName());
            writer.println("Role: " + referral.getReferringClinician().getRole());
            writer.println();

            writer.println("---- REFERRED TO ----");
            writer.println("Specialist: " + referral.getReferredToClinician().getFullName());
            writer.println("Hospital/Facility: " + referral.getReferredToFacility().getFacilityName());
            writer.println("Urgency Level: " + referral.getUrgencyLevel());
            writer.println();

            writer.println("---- CLINICAL SUMMARY ----");
            writer.println(referral.getClinicalSummary());
            writer.println();

            writer.println("---- REASON FOR REFERRAL ----");
            writer.println(referral.getReferralReason());
            writer.println();

            writer.println("---- REQUESTED INVESTIGATIONS ----");
            for (String test : referral.getRequestedInvestigations()) {
                writer.println("- " + test);
            }
            writer.println();

            writer.println("---- STATUS ----");
            writer.println("Status: " + referral.getStatus());
            writer.println("Notes: " + referral.getNotes());
            writer.println();

            writer.println("------------------------------------------");
            writer.println("Generated: " + TIMESTAMP.format(LocalDateTime.now()));
            writer.println("------------------------------------------");

            System.out.println("Referral saved to: " + fileName);

        } catch (Exception e) {
            System.out.println("Error saving referral file.");
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
            writer.println("         PRESCRIPTION SUMMARY");
            writer.println("==========================================");
            writer.println("Prescription ID: " + prescription.getPrescriptionId());
            writer.println("Issued: " + prescription.getIssueDate());
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
            writer.println("Drug: " + prescription.getMedicationName());
            writer.println("Dosage: " + prescription.getDosage());
            writer.println("Instructions: " + prescription.getInstructions());
            writer.println();

            writer.println("---- COLLECTION ----");
            writer.println("Pharmacy: " + prescription.getPharmacyName());
            writer.println("Collection Status: " + prescription.getStatus());
            writer.println();

            writer.println("------------------------------------------");
            writer.println("Generated: " + TIMESTAMP.format(LocalDateTime.now()));
            writer.println("------------------------------------------");

            System.out.println("Prescription saved to: " + fileName);

        } catch (Exception e) {
            System.out.println("Error saving prescription file.");
            e.printStackTrace();
        }
    }
}
