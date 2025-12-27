package data;

import models.Appointment;
import models.Patient;
import models.Prescription;
import models.Referral;

import java.io.File;
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
    public static void saveReferralText(Referral referral) {
        try {
            File dir = new File("output");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            FileWriter writer = new FileWriter(
                    "output/referral_" + referral.getReferralId() + ".txt"
            );

            writer.write("Referral ID: " + referral.getReferralId() + "\n");
            writer.write("Patient: " + referral.getPatient().getFullName() + "\n");
            writer.write("Clinician: " + referral.getReferredToClinician().getFullName() + "\n");
            writer.write("Summary " + referral.getClinicalSummary() + "\n");
            writer.write("Reason: " + referral.getReferralReason() + "\n");
            writer.write("Date: " + referral.getReferralDate() + "\n");

            writer.close();

        } catch (Exception e) {
            System.out.println("Error saving referral file");
            e.printStackTrace();
        }
    }





    public static void savePatient(Patient p, String filePath) {

        try (FileWriter fw = new FileWriter(filePath, true)) {

            fw.append(
                    p.getPatientId() + "," +
                            p.getFirstName() + "," +
                            p.getLastName() + "," +
                            p.getDateOfBirth() + "," +
                            p.getNhsNumber() + "," +
                            p.getGender() + "," +
                            p.getPhoneNumber() + "," +
                            p.getEmail() + "," +
                            p.getAddress() + "," +
                            p.getPostCode() + "," +
                            p.getEmergencyContactName() + "," +
                            p.getEmergencyContactPhone() + "," +
                            p.getGpSurgeryID() + "\n"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveAppointment(Appointment a, String filePath) {

        try (FileWriter fw = new FileWriter(filePath, true)) {

            fw.append(
                    a.getAppointmentId()+ "," +
                    a.getPatientId()+ "," +
                    a.getClinicianId()+ "," +
                    a.getFacilityId()+ "," +
                    a.getAppointmentDate()+ "," +
                    a.getAppointmentTime()+ "," +
                    a.getDurationTime()+ "," +
                    a.getAppointmentType()+ "," +
                    a.getStatus()+ "," +
                    a.getReasonForVisit()+ "," +
                    a.getNotes()+ "," +
                    a.getCreatedDate()+ "," +
                    a.getLastModified()+ "\n"
            );

        }catch (Exception e) {
            System.out.println("Error saving appointment");
            e.printStackTrace();
        }
    }






}
