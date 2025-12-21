package data;

import models.Patient;
import models.Prescription;
import models.Referral;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
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
    public static void savePrescriptionText(Prescription p, String folderPath) {

        String fileName = folderPath + "/prescription_" + p.getPrescriptionId() + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println("==========================================");
            writer.println("          NHS PRESCRIPTION");
            writer.println("==========================================");
            writer.println("Prescription ID: " + p.getPrescriptionId());
            writer.println("Issue Date: " + p.getIssueDate());
            writer.println();

            writer.println("---- PATIENT DETAILS ----");
            writer.println("Name: " + p.getPatient().getFullName());
            writer.println("NHS Number: " + p.getPatient().getNhsNumber());
            writer.println();

            writer.println("---- CLINICIAN ----");
            writer.println("Issued By: " + p.getClinician().getFullName());
            writer.println("Role: " + p.getClinician().getRole());
            writer.println();

            writer.println("---- MEDICATION ----");
            writer.println("Drug: " + p.getMedicationName());
            writer.println("Dosage: " + p.getDosage());
            writer.println("Frequency: " + p.getFrequency());
            writer.println("Quantity: " + p.getQuantity());
            writer.println();

            writer.println("---- INSTRUCTIONS ----");
            writer.println(p.getInstructions());
            writer.println();

            writer.println("---- PHARMACY ----");
            writer.println("Pharmacy: " + p.getPharmacyName());
            writer.println("Status: " + p.getStatus());
            writer.println();

            writer.println("==========================================");
            writer.println("Generated: " + LocalDate.now());
            writer.println("==========================================");

            System.out.println("Prescription saved: " + fileName);

        } catch (Exception e) {
            System.out.println("Error saving prescription file");
            e.printStackTrace();
        }
    }

    public static void appendPatient(Patient p, String filePath) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {

            writer.println(String.join(",",
                    p.getPatientId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getNhsNumber(),
                    p.getDateOfBirth().toString(),
                    p.getGender()
            ));

        } catch (Exception e) {
            System.out.println("Error saving patient");
            e.printStackTrace();
        }
    }





}
