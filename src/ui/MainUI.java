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

        JButton loadAppointmentsBtn = new JButton("Load Appointments");
        JButton loadPrescriptionsBtn = new JButton("Load Prescriptions");
        JButton loadReferralBtn = new JButton("Load Referrals");
        JButton loadPatientsButton = new JButton("Load Patients");





        buttonPanel.add(loadPatientsButton);
        buttonPanel.add(loadAppointmentsBtn);
        buttonPanel.add(loadPrescriptionsBtn);
        buttonPanel.add(loadReferralBtn);


        add(buttonPanel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Button actions
        loadPatientsButton.addActionListener(e -> {
            PatientUI patientsUI = new PatientUI(patientController);
            patientsUI.setVisible(true);
        });

        loadAppointmentsBtn.addActionListener(e -> {
            AppointmentUI appointmentUI = new AppointmentUI(appointmentController);
            appointmentUI.setVisible(true);

        });

        loadPrescriptionsBtn.addActionListener(e -> {
            new PrescriptionUI().setVisible(true);
        });

        loadReferralBtn.addActionListener(e -> new ReferralUI().setVisible(true));


        // Load shared data
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

        // load prescriptions
        List<Prescription> prescriptions =
                prescriptionController.getAllPrescriptions(
                        "data/prescriptions.csv",
                        patients,
                        clinicians,
                        appointments
                );
        };

    }
