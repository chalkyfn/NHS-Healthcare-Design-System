package ui;

import controller.PatientController;
import models.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.LocalDate;
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

        // BUTTON PANEL

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addPatientButton = new JButton("Add Patient");

        addPatientButton.addActionListener(e -> {
                    addPatientUI addPatientUI = new addPatientUI(this, patientController);
                    addPatientUI.setVisible(true);
        });

        buttonPanel.add(addPatientButton);


        JButton editButton = new JButton("Edit Patient");
        editButton.addActionListener(e -> editSelectedPatient());
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Patient");
        deleteButton.addActionListener(e -> deleteSelectedPatient());
        buttonPanel.add(deleteButton);



        buttonPanel.add(addPatientButton);

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

    public void addPatientToTable(Patient p) {

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


    private static String safeCell(Object value) {
        return value == null ? "" : value.toString().trim();
    }

    private void editSelectedPatient() {

        int row = patientTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient to edit",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        //patient takes firstname and last name parameter
        //I used this to add the two rows for the patient parameter
        String fullName = safeCell(tableModel.getValueAt(row, 1));
        String[] nameParts = fullName.split("\\s+");


        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String lastName  = nameParts.length > 1 ? nameParts[1] : "";

        Patient patient = new Patient(
                tableModel.getValueAt(row, 0).toString(),
                firstName,
                lastName,
                LocalDate.parse(tableModel.getValueAt(row, 2).toString()),
                tableModel.getValueAt(row, 4).toString(),
                tableModel.getValueAt(row, 5).toString(),
                tableModel.getValueAt(row, 6).toString(),
                tableModel.getValueAt(row, 7).toString(),
                tableModel.getValueAt(row, 9).toString(),
                tableModel.getValueAt(row, 10).toString(),
                tableModel.getValueAt(row, 3).toString(),
                tableModel.getValueAt(row, 8).toString(),
                LocalDate.parse(tableModel.getValueAt(row, 11).toString()),
                tableModel.getValueAt(row, 12).toString()
        );

        new addPatientUI(this, patientController, patient, row).setVisible(true);
    }

    public void updatePatientInTable(int row, Patient p) {

        tableModel.setValueAt(p.getPatientId(), row, 0);
        tableModel.setValueAt(p.getFirstName(), row, 1);
        tableModel.setValueAt(p.getDateOfBirth(), row, 2);
        tableModel.setValueAt(p.getNhsNumber(), row, 3);
        tableModel.setValueAt(p.getGender(), row, 4);
        tableModel.setValueAt(p.getPhoneNumber(), row, 5);
        tableModel.setValueAt(p.getEmail(), row, 6);
        tableModel.setValueAt(p.getAddress(), row, 7);
        tableModel.setValueAt(p.getPostCode(), row, 8);
        tableModel.setValueAt(p.getEmergencyContactName(), row, 9);
        tableModel.setValueAt(p.getEmergencyContactPhone(), row, 10);
        tableModel.setValueAt(p.getRegistrationDate(), row, 11);
        tableModel.setValueAt(p.getGpSurgeryID(), row, 12);
    }


    private void deleteSelectedPatient() {

        int row = patientTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this patient?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
        }
    }




}
