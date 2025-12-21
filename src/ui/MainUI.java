package ui;

import controller.PatientController;
import controller.AppointmentController;
import controller.PrescriptionController;
import controller.ReferralController;

import data.DataLoader;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainUI extends JFrame {

    private PatientController patientController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;

    private JTextArea outputArea;

    public MainUI() {

        // Controllers
        patientController = new PatientController();
        appointmentController = new AppointmentController();
        prescriptionController = new PrescriptionController();
        referralController = new ReferralController();

        setTitle("Healthcare Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton loadPatientsBtn = new JButton("Load Patients");
        JButton loadAppointmentsBtn = new JButton("Load Appointments");
        JButton loadPrescriptionsBtn = new JButton("Load Prescriptions");
        JButton exportPrescriptionBtn = new JButton("Export First Prescription");



        buttonPanel.add(loadPatientsBtn);
        buttonPanel.add(loadAppointmentsBtn);
        buttonPanel.add(loadPrescriptionsBtn);
        buttonPanel.add(exportPrescriptionBtn);


        add(buttonPanel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Button actions
        loadPatientsBtn.addActionListener(e -> loadPatients());
        loadAppointmentsBtn.addActionListener(e -> loadAppointments());
        loadPrescriptionsBtn.addActionListener(e -> loadPrescriptions());
        exportPrescriptionBtn.addActionListener(e -> {

// Load shared data once
            List<Facility> facilities =
                    DataLoader.loadFacilities("data/facilities.csv");

            List<Patient> patients =
                    DataLoader.loadPatients("data/patients.csv", facilities);

            List<Clinician> clinicians =
                    DataLoader.loadClinicians("data/clinicians.csv", facilities);

            List<Appointment> appointments =
                    DataLoader.loadAppointments(
                            "data/appointments.csv",
                            patients,
                            clinicians,
                            facilities
                    );

// Now load prescriptions
            List<Prescription> prescriptions =
                    prescriptionController.getAllPrescriptions(
                            "data/prescriptions.csv",
                            patients,
                            clinicians,
                            appointments
                    );


            if (!prescriptions.isEmpty()) {
                prescriptionController.generatePrescriptionFile(
                        prescriptions.get(0),
                        "output"
                );
                outputArea.append("\nPrescription file generated.\n");
            }
        });

    }

    // --------------------------------------------------
    // LOAD PATIENTS
    // --------------------------------------------------
    private void loadPatients() {
        outputArea.setText("");
        List<Patient> patients = patientController.getAllPatients();
        outputArea.append("Patient ID   -   " +
                "Full Name   -   " +
                "Date of Birth   -   " +
                "NHS Number   -   " +
                "Gender   -   " +
                "Phone Number   -   " +
                "Email   -   " +
                "Address   -   " +
                "Post Code   -   " +
                "Emergency Contact   -   " +
                "Emergency Phone   -   " +
                "Registration Date\n");
        for (Patient p : patients) {
            outputArea.append(
                    p.getPatientId() + "   -   " +
                            p.getFullName() + "   -   " +
                            p.getDateOfBirth() + "   -   " +
                            p.getNhsNumber() + "   -   " +
                            p.getGender() + "   -   " +
                            p.getPhoneNumber() + "   -   " +
                            p.getEmail() + "   -   " +
                            p.getAddress() + "   -   " +
                            p.getPostCode() + "   -   " +
                            p.getEmergencyContactName() + "   -   "+
                            p.getEmergencyContactPhone() + "   -   " +
                            p.getRegistrationDate() + "\n"


            );
        }
    }

    // --------------------------------------------------
    // LOAD APPOINTMENTS
    // --------------------------------------------------
    private void loadAppointments() {
        outputArea.setText("");
        List<Appointment> appointments = appointmentController.getAllAppointments();
        outputArea.append("Appointment ID   -   " +
                "Patient ID -   " +
                "Clinician ID   -   " +
                "Facility ID  -   " +
                "Appointment Date  -   " +
                "Duration Minutes  -   " +
                "Appointment Type  -   " +
                "Status  -   " +
                "Notes   -   " +
                "Created Date  -   " +
                "Last Date Modified  -   "  + "\n");
        for (Appointment a : appointments) {
            outputArea.append(
                    a.getAppointmentId() + " - " +
                            a.getPatientId() + " - " +
                            a.getAppointmentId() + " - " +
                            a.getClinianId() + " - " +
                            a.getFacilityId() + " - " +
                            a.getAppointmentDate() + " - " +
                            a.getAppointmentDate() + " - " +
                            a.getAppointmentTime() + " - " +
                            a.getDurationTime() + " - " +
                            a.getAppointmentType() + " - " +
                            a.getStatus() + " - " +
                            a.getReasonForVisit() + " - " +
                            a.getNotes()+ " - " +
                            a.getCreatedDate() + " - " +
                            a.getLastModified() + " - " +
                            a.getStatus() + "\n"
            );
        }
    }

    // --------------------------------------------------
    // LOAD PRESCRIPTIONS
    // --------------------------------------------------
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
