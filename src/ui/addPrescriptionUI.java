package ui;

import controller.PrescriptionController;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class addPrescriptionUI extends JFrame {


    private final PrescriptionUI parent;
    private Prescription editingPrescription;
    private int editingRow = -1;

    private JTextField prescriptionIdField;
    private JTextField patientIdField;
    private JTextField clinicianIdField;
    private JTextField appointmentIdField;
    private JTextField prescriptionDateField;
    private JTextField medicationNameField;
    private JTextField dosageField;
    private JTextField frequencyField;
    private JTextField durationField;
    private JTextField quantityField;
    private JTextField instructionField;
    private JTextField pharmacyField;
    private JTextField statusField;
    private JTextField issueDateField;
    private JTextField collectionDateField;

    private PrescriptionController prescriptionController;

    public addPrescriptionUI(PrescriptionUI parent , PrescriptionController controller) {
        this.parent = parent;
        this.prescriptionController = controller;
        setTitle("Add Prescription");
        initUI();
    }

    // ---------- EDIT ----------
    public addPrescriptionUI(PrescriptionUI parent, Prescription p, int row) {
        this.parent = parent;
        this.editingPrescription = p;
        this.editingRow = row;
        setTitle("Edit Prescription");
        initUI();
        fillFields(p);
    }

    private void initUI() {
        setSize(650, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.setLayout(new GridLayout(8, 4, 15, 12));


        prescriptionIdField = addField(panel, "Prescription ID: ");
        patientIdField = addField(panel, "Patient ID: ");
        clinicianIdField = addField(panel, "Clinician ID: ");
        appointmentIdField = addField(panel, "Appointment ID: ");
        prescriptionDateField = addField(panel, "Prescription Date: ");
        medicationNameField = addField(panel, "Medication Name");
        dosageField = addField(panel, "Dosage: ");
        frequencyField = addField(panel, "Frequency");
        durationField = addField(panel, "Duration: ");
        quantityField = addField(panel, "Quantity");
        instructionField = addField(panel, "Instructions");
        pharmacyField = addField(panel, "Pharmacy Name: ");
        statusField = addField(panel, "Status");
        issueDateField = addField(panel, "Date Issued: ");
        collectionDateField = addField(panel, "Collection Date: ");

        JButton saveButton = new JButton("Save Prescription");
        saveButton.addActionListener(e -> savePrescription());


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTextField addField(JPanel panel, String label) {
        JTextField field = new JTextField();
        panel.add(new JLabel(label));
        panel.add(field);
        return field;
    }

    private void fillFields(Prescription p) {
        prescriptionIdField.setText(p.getPrescriptionId());
        patientIdField.setText(p.getPatient().getPatientId());
        clinicianIdField.setText(p.getClinician().getClinicianId());
        appointmentIdField.setText(p.getAppointment().getAppointmentId());
        prescriptionDateField.setText(p.getPrescriptionDate().toString());
        medicationNameField.setText(p.getMedicationName());
        dosageField.setText(p.getDosage());
        frequencyField.setText(p.getFrequency());
        durationField.setText(String.valueOf(p.getDurationDays()));
        quantityField.setText(p.getQuantity());
        instructionField.setText(p.getInstructions());
        statusField.setText(p.getStatus());
    }

    private void savePrescription() {

        Patient patient = prescriptionController
                .findPatientById(patientIdField.getText());

        Clinician clinician = prescriptionController
                .findClinicianById(clinicianIdField.getText());

        Appointment appointment = prescriptionController
                .findAppointmentById(appointmentIdField.getText());
        try {
            Prescription prescription = new Prescription(
                    prescriptionIdField.getText(),

                    patient,
                    clinician,
                    appointment,

                    LocalDate.parse(prescriptionDateField.getText()),
                    medicationNameField.getText(),
                    dosageField.getText(),
                    frequencyField.getText(),
                    Integer.parseInt(durationField.getText()),
                    quantityField.getText(),
                    instructionField.getText(),
                    pharmacyField.getText(),
                    statusField.getText(),
                    LocalDate.parse(issueDateField.getText()),
                    LocalDate.parse(collectionDateField.getText())
            );

            // ADD vs EDIT

            if (editingPrescription == null) {
                parent.addPrescriptionToTable(prescription);
            } else {
                parent.updatePrescriptionInTable(editingRow, prescription);
            }

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid input.\nCheck dates (yyyy-MM-dd) and numbers.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }

}
