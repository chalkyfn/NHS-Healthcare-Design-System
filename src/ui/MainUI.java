package ui;

import controller.PatientController;
import controller.AppointmentController;
import controller.PrescriptionController;
import controller.ReferralController;

import models.Patient;
import models.Appointment;

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

        buttonPanel.add(loadPatientsBtn);
        buttonPanel.add(loadAppointmentsBtn);

        add(buttonPanel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Button actions
        loadPatientsBtn.addActionListener(e -> loadPatients());
        loadAppointmentsBtn.addActionListener(e -> loadAppointments());
    }

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
}
