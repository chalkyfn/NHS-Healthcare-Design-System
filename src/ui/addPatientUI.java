package ui;

import controller.PatientController;
import models.Patient;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class addPatientUI extends JFrame {

    private final PatientUI parent;
    private final PatientController patientController;

    private JTextField idField, firstNameField,lastNameField, dobField, nhsField,
            genderField, phoneField, emailField,
            addressField, postCodeField,
            emergencyNameField, emergencyPhoneField, gpSugeryField,TYPESHIT;

    public addPatientUI(PatientUI parent, PatientController controller) {
        this.parent = parent;
        this.patientController = controller;

        setTitle("Add Patient");
        setSize(660, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(12,10));
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

            patientController.addPatient(patient);


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
