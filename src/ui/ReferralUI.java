package ui;

import controller.ReferralController;
import data.DataLoader;
import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ReferralUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private ReferralController referralController;

    public ReferralUI() {

        referralController = new ReferralController();

        setTitle("Referrals");
        setSize(1100, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        initButtons();
        loadReferrals();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{
                        "Referral ID",
                        "Patient",
                        "Referring Clinician",
                        "Referred To",
                        "From Facility",
                        "To Facility",
                        "Referral Date",
                        "Urgency",
                        "Referral Reason",
                        "Clinical Summary",
                        "Requested Investigations",
                        "Status",
                        "AppointmentID",
                        "Notes",
                        "Created Date",
                        "Last Updated"
                },
                0
        );

        table = new JTable(model);
        table.setRowHeight(22);
        table.setFillsViewportHeight(true);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // BUTTONS
    private void initButtons() {

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton exportBtn = new JButton("Export");


        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(exportBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadReferrals() {

        model.setRowCount(0);

        // Load dependencies
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

        List<Referral> referrals =
                referralController.getAllReferrals(
                        patients,
                        clinicians,
                        facilities,
                        appointments
                );

        for (Referral r : referrals) {

            model.addRow(new Object[]{
                    r.getReferralId(),
                    r.getPatient() != null
                            ? r.getPatient().getPatientId()
                            : "null",
                    r.getReferringClinician() != null
                            ? r.getReferringClinician().getClinicianId()
                            : "null",
                    r.getReferredToClinician() != null
                            ? r.getReferredToClinician().getClinicianId()
                            : "null",
                    r.getReferringFacility() != null
                            ? r.getReferringFacility().getFacilityId()
                            : "null",
                    r.getReferredToFacility() != null
                            ? r.getReferredToFacility().getFacilityId()
                            : "null",
                    r.getReferralDate(),
                    r.getUrgencyLevel(),
                    r.getReferralReason(),
                    r.getClinicalSummary(),
                    r.getRequestedInvestigations(),
                    r.getStatus(),
                    r.getAppointment() != null
                        ? r.getAppointment().getAppointmentId()
                        : "null",
                    r.getNotes(),
                    LocalDate.now(),
                    r.getLastUpdated(),
                    r.getStatus()
            });
        }
    }
}
