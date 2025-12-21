package ui;

import controller.PatientController;
import models.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientUI extends JFrame {

    private PatientController patientController;
    private JTable patientTable;
    private DefaultTableModel tableModel;

    public PatientUI(PatientController patientController) {
        this.patientController = patientController;

        setTitle("Patients");
        setSize(1100, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadPatients();
    }

    private void initUI() {

        String[] columns = {
                "Patient ID",
                "Full Name",
                "Date of Birth",
                "NHS Number",
                "Gender",
                "Phone Number",
                "Email",
                "Address",
                "Post Code",
                "Emergency Contact",
                "Emergency Phone",
                "Registration Date",
                "GP Surgery ID"
        };

        tableModel = new DefaultTableModel(columns, 0);
        patientTable = new JTable(tableModel);

        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.setRowHeight(22);
        patientTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(patientTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addPatientButton = new JButton("Add Patient");
        JButton refreshButton = new JButton("Refresh");

        addPatientButton.addActionListener(e -> {
            addPatientUI addPatientUI = new addPatientUI(this, patientController);
            addPatientUI.setVisible(true);
        });

        refreshButton.addActionListener(e -> loadPatients());

        buttonPanel.add(addPatientButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

    }

    private void loadPatients() {
        tableModel.setRowCount(0);

        List<Patient> patients = patientController.getAllPatients();

        for (Patient p : patients) {
            tableModel.addRow(new Object[]{
                    p.getPatientId(),
                    p.getFullName(),
                    p.getDateOfBirth(),
                    p.getNhsNumber(),
                    p.getGender(),
                    p.getPhoneNumber(),
                    p.getEmail(),
                    p.getAddress(),
                    p.getPostCode(),
                    p.getEmergencyContactName(),
                    p.getEmergencyContactPhone(),
                    p.getRegistrationDate(),
                    p.getGpSurgeryID()
            });
        }
    }
}
