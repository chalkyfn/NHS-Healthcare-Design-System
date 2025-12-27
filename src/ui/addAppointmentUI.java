package ui;

import controller.AppointmentController;
import data.DataLoader;
import models.Appointment;
import models.Clinician;
import models.Facility;
import models.Patient;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class addAppointmentUI extends JFrame {

    private final AppointmentUI parent;

    private Appointment editingAppointment;
    private int editingRow = -1;

    private AppointmentController appointmentController;

    private JTextField appointmentIdField;
    private JTextField patientIdField;
    private JTextField clinicianIdField;
    private JTextField facilityIdField;
    private JTextField appointmentDateField;
    private JTextField appointmentTimeField;
    private JTextField appointmentDurationField;
    private JTextField appointmentTypeField;
    private JTextField appointmentStatusField;
    private JTextField reasonForVisitField;
    private JTextField appointmentNotesField;

    private List<Patient> patients;
    private List<Clinician> clinicians;
    private List<Facility> facilities;

    public addAppointmentUI(AppointmentUI parent,
                            AppointmentController controller) {

        this.parent = parent;
        this.appointmentController = controller;

        setTitle("Add Appointment");
        setSize(650, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    public addAppointmentUI(AppointmentUI parent,AppointmentController controller, Appointment appointment, int row) {
        this.parent = parent;
        this.appointmentController = controller;
        this.editingAppointment = appointment;
        this.editingRow = row;

        setTitle("Edit Appointment");
        initUI();
        fillFields(appointment);
    }

    private void loadData() {
        facilities = DataLoader.loadFacilities("data/facilities.csv");
        patients = DataLoader.loadPatients("data/patients.csv", facilities);
        clinicians = DataLoader.loadClinicians("data/clinicians.csv", facilities);
    }



    private void initUI() {
        setSize(650, 520);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.setLayout(new GridLayout(8, 4,15, 12));

        appointmentIdField = addField(panel, "Appointment ID:");
        patientIdField = addField(panel, "Patient ID:");
        clinicianIdField = addField(panel, "Clinician ID:");
        facilityIdField = addField(panel, "Facility ID:");
        appointmentDateField = addField(panel, "Date (yyyy-mm-dd):");
        appointmentTimeField = addField(panel, "Time (HH:mm):");
        appointmentDurationField = addField(panel, "Duration (minutes):");
        appointmentTypeField = addField(panel, "Appointment Type:");
        appointmentStatusField = addField(panel, "Status:");
        reasonForVisitField = addField(panel, "Reason for Visit:");
        appointmentNotesField = addField(panel, "Notes:");

        JButton saveButton = new JButton("Save Appointment");

        saveButton.addActionListener(e -> {
            System.out.println("SAVE BUTTON CLICKED");
            saveAppointment();
        });
        panel.add(new JLabel());
        panel.add(saveButton);

        add(panel);
        setVisible(true);
    }

    private JTextField addField(JPanel panel, String label) {
        JTextField field = new JTextField();
        panel.add(new JLabel(label));
        panel.add(field);
        return field;
    }

    private void fillFields(Appointment a) {
        appointmentIdField.setText(a.getAppointmentId());
        appointmentIdField.setEditable(false);

        patientIdField.setText(a.getPatient().getPatientId());
        clinicianIdField.setText(a.getClinician().getClinicianId());
        facilityIdField.setText(a.getFacility().getFacilityId());
        appointmentDateField.setText(a.getAppointmentDate().toString());
        appointmentTimeField.setText(a.getAppointmentTime().toString());
        appointmentDurationField.setText(String.valueOf(a.getDurationTime()));
        appointmentTypeField.setText(a.getAppointmentType());
        appointmentStatusField.setText(a.getStatus());
        reasonForVisitField.setText(a.getReasonForVisit());
        appointmentNotesField.setText(a.getNotes());
    }

    private void saveAppointment() {

        Patient patient = appointmentController
                .findPatientById(patientIdField.getText());

        Clinician clinician = appointmentController
                .findClinicianById(clinicianIdField.getText());

        Facility facility = appointmentController
                .findFacilityById(facilityIdField.getText());

        if (patient == null || clinician == null || facility == null) {
            JOptionPane.showMessageDialog(this,
                    "Invalid Patient, Clinician, or Facility ID",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Appointment appointment = new Appointment(
                    appointmentIdField.getText(),
                    patient,
                    clinician,
                    facility,
                    LocalDate.parse(appointmentDateField.getText()),
                    LocalTime.parse(appointmentTimeField.getText()),
                    Integer.parseInt(appointmentDurationField.getText()),
                    appointmentTypeField.getText(),
                    appointmentStatusField.getText(),
                    reasonForVisitField.getText(),
                    appointmentNotesField.getText(),
                    LocalDate.now(),
                    LocalDate.now()
            );
            if (editingAppointment == null) {
                parent.addAppointmentToTable(appointment);   // ADD
            } else {
                parent.updateAppointmentInTable(editingRow, appointment); // EDIT
            }

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid input.\nDate: yyyy-mm-dd\nTime: HH:mm\nDuration must be a number",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
