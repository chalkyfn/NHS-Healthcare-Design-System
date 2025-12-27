package ui;

import controller.PatientController;
import models.Patient;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class addPatientUI extends JFrame {

    private final PatientUI parent;
    private final PatientController patientController;

    private Patient editingPatient;
    private int editingRow = -1;

    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JTextField nhsField;
    private JTextField genderField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField postCodeField;
    private JTextField emergencyNameField;
    private JTextField emergencyPhoneField;
    private JTextField gpSugeryField;

    public addPatientUI(PatientUI parent, PatientController controller) {
        this.parent = parent;
        this.patientController = controller;

        setTitle("Add Patient");
        setSize(660, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    public addPatientUI(PatientUI parent,
                        PatientController controller,
                        Patient patient,
                        int row) {

        this.parent = parent;
        this.patientController = controller;
        this.editingPatient = patient;
        this.editingRow = row;

        setTitle("Edit Patient");


        initUI();
        fillFields(patient);
    }

    private void initUI() {
        setSize(660, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setLayout(new GridLayout(14, 2, 10, 10));


        idField = addField(panel, "Patient ID:");
        firstNameField = addField(panel, "First Name:");
        lastNameField = addField(panel, "Last Name");
        dobField = addField(panel, "Date of Birth (yyyy-mm-dd):");
        nhsField = addField(panel, "NHS Number:");
        genderField = addField(panel, "Gender:");
        phoneField = addField(panel, "Phone:");
        emailField = addField(panel, "Email:");
        addressField = addField(panel, "Address:");
        postCodeField = addField(panel, "Post Code:");
        emergencyNameField = addField(panel, "Emergency Contact:");
        emergencyPhoneField = addField(panel, "Emergency Phone:");
        gpSugeryField = addField(panel, "GP Surgery ID:");


        JButton saveButton = new JButton("Save Patient");
        saveButton.addActionListener(e -> savePatient());

        panel.add(new JLabel());
        panel.add(saveButton);

        add(panel);
    }

    private void fillFields(Patient p) {

        idField.setText(p.getPatientId());
        firstNameField.setText(p.getFirstName());
        lastNameField.setText(p.getLastName());
        dobField.setText(p.getDateOfBirth().toString());
        nhsField.setText(p.getNhsNumber());
        genderField.setText(p.getGender());
        phoneField.setText(p.getPhoneNumber());
        emailField.setText(p.getEmail());
        addressField.setText(p.getAddress());
        postCodeField.setText(p.getPostCode());
        emergencyNameField.setText(p.getEmergencyContactName());
        emergencyPhoneField.setText(p.getEmergencyContactPhone());
        gpSugeryField.setText(p.getGpSurgeryID());
    }

    private JTextField addField(JPanel panel, String label) {
        JTextField field = new JTextField();
        panel.add(new JLabel(label));
        panel.add(field);
        return field;
    }

    private void savePatient() {
        try {
            LocalDate dob = LocalDate.parse(dobField.getText());

            Patient patient = new Patient(
                    idField.getText(),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    LocalDate.parse(dobField.getText()),
                    genderField.getText(),
                    phoneField.getText(),
                    emailField.getText(),
                    addressField.getText(),
                    emergencyNameField.getText(),
                    emergencyPhoneField.getText(),
                    nhsField.getText(),
                    postCodeField.getText(),
                    LocalDate.now(),
                    gpSugeryField.getText()
            );

            if (editingPatient == null) {
                parent.addPatientToTable(patient);   // ADD
            } else {
                parent.updatePatientInTable(editingRow, patient); // EDIT
            }

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid input. Make sure the date is yyyy-mm-dd",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
