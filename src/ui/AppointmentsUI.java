package ui;

import controller.AppointmentController;
import models.Appointment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AppointmentsUI extends JFrame {

    private JTextArea outputArea;
    private AppointmentController appointmentController;

    public AppointmentsUI() {

        appointmentController = new AppointmentController();

        setTitle("Appointments");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // load appointments when UI opens
        loadAppointments();
    }

    // --------------------------------------------------
    // LOAD APPOINTMENTS
    // --------------------------------------------------
    private void loadAppointments() {
        outputArea.setText("");
        List<Appointment> appointments = appointmentController.getAllAppointments();
        outputArea.append("Appointment ID   -   " +
                "Patient ID -   " +
                "Clinician ID   -   " +
                "Facility ID  -   " +
                "Appointment Date  -   " +
                "Duration Minutes  -   " +
                "Appointment Type  -   " +
                "Status  -   " +
                "Notes   -   " +
                "Created Date  -   " +
                "Last Date Modified  -   "  + "\n");
        for (Appointment a : appointments) {
            outputArea.append(
                    a.getAppointmentId() + " - " +
                            a.getPatientId() + " - " +
                            a.getAppointmentId() + " - " +
                            a.getClinianId() + " - " +
                            a.getFacilityId() + " - " +
                            a.getAppointmentDate() + " - " +
                            a.getAppointmentDate() + " - " +
                            a.getAppointmentTime() + " - " +
                            a.getDurationTime() + " - " +
                            a.getAppointmentType() + " - " +
                            a.getStatus() + " - " +
                            a.getReasonForVisit() + " - " +
                            a.getNotes()+ " - " +
                            a.getCreatedDate() + " - " +
                            a.getLastModified() + " - " +
                            a.getStatus() + "\n"
            );
        }
    }
}
