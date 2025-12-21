package ui;

import controller.PrescriptionController;
import data.DataLoader;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PrescriptionUI extends JFrame {

    private final JTextArea outputArea;
    private final PrescriptionController prescriptionController;

    public PrescriptionUI() {

        prescriptionController = new PrescriptionController();

        setTitle("Prescriptions");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // 👇 AUTO-LOAD DATA WHEN UI OPENS
        loadPrescriptions();
    }


    private void loadPrescriptions() {

        outputArea.setText("");

        List<Facility> facilities =
                DataLoader.loadFacilities("data/facilities.csv");

        List<Patient> patients =
                DataLoader.loadPatients("data/patients.csv", facilities);

        List<Clinician> clinicians =
                DataLoader.loadClinicians("data/clinicians.csv", facilities);

        List<Appointment> appointments =
                DataLoader.loadAppointments("data/appointments.csv", patients,
                        clinicians,
                        facilities);


        List<Prescription> prescriptions =
                prescriptionController.getAllPrescriptions(
                        "data/prescriptions.csv",
                        patients,
                        clinicians,
                        appointments
                );

        outputArea.append(
                "Prescription ID | Patient | Clinician | Appointment | Date | Medication | " +
                        "Dosage | Frequency | Duration | Quantity | Instructions | Pharmacy | Status | Issue Date | Collection Date\n"
        );
        outputArea.append("-------------------------------------------------------------------------------------------------------------\n");

        for (Prescription p : prescriptions) {
            outputArea.append(
                    p.getPrescriptionId() + " | " +
                            p.getPatient().getFullName() + " | " +
                            p.getClinician().getFullName() + " | " +
                            p.getAppointmentId() + " | " +
                            p.getPrescriptionDate() + " | " +
                            p.getMedicationName() + " | " +
                            p.getDosage() + " | " +
                            p.getFrequency() + " | " +
                            p.getDurationDays() + " | " +
                            p.getQuantity() + " | " +
                            p.getInstructions() + " | " +
                            p.getPharmacyName() + " | " +
                            p.getStatus() + " | " +
                            p.getIssueDate() + " | " +
                            p.getCollectionDate() + "\n"
            );
        }

    }
}
