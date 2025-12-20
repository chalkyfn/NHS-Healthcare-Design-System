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

        buttonPanel.add(loadPatientsBtn);
        buttonPanel.add(loadAppointmentsBtn);
        buttonPanel.add(loadPrescriptionsBtn);

        add(buttonPanel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Button actions
        loadPatientsBtn.addActionListener(e -> loadPatients());
        loadAppointmentsBtn.addActionListener(e -> loadAppointments());
        loadPrescriptionsBtn.addActionListener(e -> loadPrescriptions());
    }

    // --------------------------------------------------
    // LOAD PATIENTS
    // --------------------------------------------------
    private void loadPatients() {
        outputArea.setText("");
        List<Patient> patients = patientController.getAllPatients();

        for (Patient p : patients) {
            outputArea.append(
                    p.getPatientId() + " - " +
                            p.getFullName() + "\n"
            );
        }
    }

    // --------------------------------------------------
    // LOAD APPOINTMENTS
    // --------------------------------------------------
    private void loadAppointments() {
        outputArea.setText("");
        List<Appointment> appointments = appointmentController.getAllAppointments();

        for (Appointment a : appointments) {
            outputArea.append(
                    a.getAppointmentId() + " - " +
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

        List<Prescription> prescriptions =
                prescriptionController.getAllPrescriptions(
                        "data/prescriptions.csv",
                        patients,
                        clinicians
                );

        for (Prescription p : prescriptions) {
            outputArea.append(
                    p.getPrescriptionId() + " | " +
                            p.getMedicationName() + " | " +
                            p.getPatient().getFullName() + " | " +
                            p.getStatus() + "\n"
            );
        }
    }

}
