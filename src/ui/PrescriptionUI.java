package ui;

import controller.PrescriptionController;
import data.DataLoader;
import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PrescriptionUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private final PrescriptionController prescriptionController;

    public PrescriptionUI() {

        prescriptionController = new PrescriptionController();

        setTitle("Prescriptions");
        setSize(1200, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initTable();
        initButtons();
        loadPrescriptions();

        setVisible(true);
    }

    // ---------------- TABLE ----------------
    private void initTable() {

        String[] columns = {
                "Prescription ID",
                "Patient ID",
                "Clinician ID",
                "Appointment ID",
                "Prescription Date",
                "Medication",
                "Dosage",
                "Frequency",
                "Duration (days)",
                "Quantity",
                "Instructions",
                "Pharmacy",
                "Status",
                "Issue Date",
                "Collection Date"
        };

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        table.setRowHeight(22);
        table.setAutoCreateRowSorter(true);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // BUTTONS
    private void initButtons() {

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e ->
                new addPrescriptionUI(this, prescriptionController).setVisible(true)
        );

        editBtn.addActionListener(e -> editSelectedPrescription());
        deleteBtn.addActionListener(e -> deleteSelectedPrescription());

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // ---------------- LOAD ----------------
    private void loadPrescriptions() {

        tableModel.setRowCount(0);

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

        List<Prescription> prescriptions =
                prescriptionController.getAllPrescriptions(
                        "data/prescriptions.csv",
                        patients,
                        clinicians,
                        appointments
                );

        for (Prescription p : prescriptions) {
            tableModel.addRow(new Object[]{
                    p.getPrescriptionId(),
                    p.getPatient().getPatientId(),
                    p.getClinician().getClinicianId(),
                    p.getAppointment() != null ? p.getAppointment().getAppointmentId() : "N/A",
                    p.getPrescriptionDate(),
                    p.getMedicationName(),
                    p.getDosage(),
                    p.getFrequency(),
                    p.getDurationDays(),
                    p.getQuantity(),
                    p.getInstructions(),
                    p.getPharmacyName(),
                    p.getStatus(),
                    p.getIssueDate(),
                    p.getCollectionDate()
            });
        }
    }

    // ---------------- ADD ----------------
    public void addPrescriptionToTable(Prescription p) {
        tableModel.addRow(new Object[]{
                p.getPrescriptionId(),
                p.getPatient().getPatientId(),
                p.getClinician().getClinicianId(),
                p.getAppointment().getAppointmentId(),
                p.getPrescriptionDate(),
                p.getMedicationName(),
                p.getDosage(),
                p.getFrequency(),
                p.getDurationDays(),
                p.getQuantity(),
                p.getInstructions(),
                p.getPharmacyName(),
                p.getStatus(),
                p.getIssueDate(),
                p.getCollectionDate()
        });
    }

    // ---------------- UPDATE ----------------
    public void updatePrescriptionInTable(int row, Prescription p) {
        tableModel.setValueAt(p.getPrescriptionId(), row, 0);
        tableModel.setValueAt(p.getPatient().getPatientId(), row, 1);
        tableModel.setValueAt(p.getClinician().getClinicianId(), row, 2);
        tableModel.setValueAt(p.getAppointment().getAppointmentId(), row, 3);
        tableModel.setValueAt(p.getPrescriptionDate(), row, 4);
        tableModel.setValueAt(p.getMedicationName(), row, 5);
        tableModel.setValueAt(p.getDosage(), row, 6);
        tableModel.setValueAt(p.getFrequency(), row, 7);
        tableModel.setValueAt(p.getDurationDays(), row, 8);
        tableModel.setValueAt(p.getQuantity(), row, 9);
        tableModel.setValueAt(p.getInstructions(), row, 10);
        tableModel.setValueAt(p.getPharmacyName(), row, 11);
        tableModel.setValueAt(p.getStatus(), row, 12);
        tableModel.setValueAt(p.getIssueDate(), row, 13);
        tableModel.setValueAt(p.getCollectionDate(), row, 14);
    }

    // ---------------- DELETE ----------------
    private void deleteSelectedPrescription() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a prescription first");
            return;
        }
        tableModel.removeRow(row);
    }

    // ---------------- EDIT ----------------
    private void editSelectedPrescription() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a prescription first");
            return;
        }

        String patientId = tableModel.getValueAt(row, 1).toString();
        String clinicianId = tableModel.getValueAt(row, 2).toString();
        String appointmentId = tableModel.getValueAt(row, 3).toString();

        Patient patient = prescriptionController.findPatientById(patientId);
        Clinician clinician = prescriptionController.findClinicianById(clinicianId);
        Appointment appointment = prescriptionController.findAppointmentById(appointmentId);

        if (patient == null || clinician == null) {
            JOptionPane.showMessageDialog(this,
                    "Linked Patient or Clinician not found",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }


        Prescription p = new Prescription(
                tableModel.getValueAt(row, 0).toString(),
                patient,
                clinician,
                appointment,
                LocalDate.parse(tableModel.getValueAt(row, 4).toString()),
                tableModel.getValueAt(row, 5).toString(),
                tableModel.getValueAt(row, 6).toString(),
                tableModel.getValueAt(row, 7).toString(),
                Integer.parseInt(tableModel.getValueAt(row, 8).toString()),
                tableModel.getValueAt(row, 9).toString(),
                tableModel.getValueAt(row, 10).toString(),
                tableModel.getValueAt(row, 11).toString(),
                tableModel.getValueAt(row, 12).toString(),
                LocalDate.parse(tableModel.getValueAt(row, 13).toString()),
                LocalDate.parse(tableModel.getValueAt(row, 14).toString())
        );

        new addPrescriptionUI(this, p, row).setVisible(true);
    }
}
