package ui;

import controller.AppointmentController;
import models.Appointment;
import models.Clinician;
import models.Facility;
import models.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentUI extends JFrame {

    private final AppointmentController  appointmentController;
    private JTable table;
    private DefaultTableModel model;

    public AppointmentUI(AppointmentController appointmentController) {
        this.appointmentController = appointmentController;

        setTitle("Appointments");
        setSize(1100, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadAppointments();


    }

    private void initUI() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{
                        "Appointment ID",
                        "Patient ID",
                        "Clinician ID",
                        "Facility ID",
                        "Appointment Date",
                        "Appointment Time",
                        "Duration",
                        "Appointment Type",
                        "Status",
                        "Reason",
                        "Notes"
                },
                0
        );

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // ---------- BUTTON PANEL ----------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addButton = new JButton("Add Appointment");
        addButton.addActionListener(e ->
                new addAppointmentUI(this, appointmentController).setVisible(true)
        );

        buttonPanel.add(addButton);

        add(buttonPanel, BorderLayout.SOUTH);

        JButton editButton = new JButton("Edit Appointment");
        editButton.addActionListener(e -> editSelectedAppointment());
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Appointment");
        deleteButton.addActionListener(e -> deleteSelectedAppointment());
        buttonPanel.add(deleteButton);


    }

    private void loadAppointments() {
        model.setRowCount(0);

        List<Appointment> appointments = appointmentController.getAllAppointments();

        for (Appointment a : appointments) {
            model.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getClinicianId(),
                    a.getFacilityId(),
                    a.getAppointmentDate(),
                    a.getAppointmentTime(),
                    a.getDurationTime(),
                    a.getAppointmentType(),
                    a.getStatus(),
                    a.getReasonForVisit(),
                    a.getNotes(),
                    a.getCreatedDate(),
                    a.getLastModified()
            });
        }
    }

    public void addAppointmentToTable(Appointment a) {
        model.addRow(new Object[]{
                a.getAppointmentId(),
                a.getPatientId(),
                a.getClinicianId(),
                a.getFacilityId(),
                a.getAppointmentDate(),
                a.getAppointmentTime(),
                a.getDurationTime(),
                a.getAppointmentType(),
                a.getStatus(),
                a.getReasonForVisit(),
                a.getNotes(),
                a.getCreatedDate(),
                a.getLastModified()
        });
    }

    private void editSelectedAppointment() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an appointment to edit",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Patient patient = appointmentController
                .findPatientById(model.getValueAt(row, 1).toString());

        Clinician clinician = appointmentController
                .findClinicianById( model.getValueAt(row, 2).toString());

        Facility facility = appointmentController
                .findFacilityById(model.getValueAt(row, 3).toString());

        Appointment appointment = new Appointment(
                model.getValueAt(row, 0).toString(), // Appointment ID
                patient,
                clinician,
                facility,
                LocalDate.parse(model.getValueAt(row, 4).toString()),
                LocalTime.parse(model.getValueAt(row, 5).toString()),
                Integer.parseInt(model.getValueAt(row, 6).toString()),
                model.getValueAt(row, 7).toString(),
                model.getValueAt(row, 8).toString(),
                model.getValueAt(row, 9).toString(),
                model.getValueAt(row, 10).toString(),
                LocalDate.now(),
                LocalDate.now()
        );

        new addAppointmentUI(this, appointmentController,appointment,row).setVisible(true);
    }


    public void updateAppointmentInTable(int row, Appointment a) {

        model.setValueAt(a.getAppointmentId(), row, 0);
        model.setValueAt(a.getPatient().getPatientId(), row, 1);
        model.setValueAt(a.getClinician().getClinicianId(), row, 2);
        model.setValueAt(a.getFacility().getFacilityId(), row, 3);
        model.setValueAt(a.getAppointmentDate(), row, 4);
        model.setValueAt(a.getAppointmentTime(), row, 5);
        model.setValueAt(a.getDurationTime(), row, 6);
        model.setValueAt(a.getAppointmentType(), row, 7);
        model.setValueAt(a.getStatus(), row, 8);
        model.setValueAt(a.getReasonForVisit(), row, 9);
        model.setValueAt(a.getNotes(), row,10);
    }

    private void deleteSelectedAppointment() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an appointment to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this appointment?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(row);
        }
    }



}
