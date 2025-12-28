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

        String[] columns = {
                "Referral ID",
                "Patient",
                "Clinician",
                "Appointment",
                "Reason",
                "Priority",
                "Status",
                "Referral Date"
        };

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

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            new addReferralUI(this, referralController).setVisible(true);
        });


        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> editSelectedReferral());


        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteSelectedReferral());
        buttonPanel.add(deleteButton);


        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(e -> {
            Referral selected = getSelectedReferral();
            if (selected != null) {
                referralController.exportReferral(selected);
                JOptionPane.showMessageDialog(this, "Referral exported successfully");
            }
        });



        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);

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

    private Referral getSelectedReferral() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a referral",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return null;
        }

        String referralId = table.getValueAt(row, 0).toString();

        return referralController.findReferralById(referralId);
    }


    public void addReferralToTable(Referral r) {

        String appointmentId =
                r.getAppointment() != null
                        ? r.getAppointment().getAppointmentId()
                        : "N/A";

        model.addRow(new Object[]{
                r.getReferralId(),
                r.getPatient().getPatientId(),
                r.getReferringClinicianId(),
                r.getReferredToClinicianId(),
                r.getReferringFacilityId(),
                r.getReferredToFacilityId(),
                r.getReferralDate(),
                r.getUrgencyLevel(),
                r.getReferralReason(),
                r.getClinicalSummary(),
                r.getRequestedInvestigations(),
                r.getStatus(),
                appointmentId,
                r.getNotes(),
                r.getCreatedDate(),
                r.getLastUpdated()
        });
    }

    private void editSelectedReferral() {

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a referral to edit",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // read values from table
        String referralId = model.getValueAt(row, 0).toString();
        String patientId = model.getValueAt(row, 1).toString();
        String clinicianId = model.getValueAt(row, 2).toString();
        String referredTo = model.getValueAt(row, 3).toString();
        String fromFacilityId = model.getValueAt(row, 4).toString();
        String toFacilityId = model.getValueAt(row, 5).toString();

        LocalDate referralDate =
                LocalDate.parse(model.getValueAt(row, 6).toString());

        String urgency = model.getValueAt(row, 7).toString();
        String referralReason = model.getValueAt(row, 8).toString();
        String clinicalSummary = model.getValueAt(row, 9).toString();
        String investigations = model.getValueAt(row, 10).toString();
        String status = model.getValueAt(row, 11).toString();
        String appointmentId = model.getValueAt(row, 12).toString();
        String notes = model.getValueAt(row, 13).toString();

        // Look up linked objects
        Patient patient = referralController.findPatientById(patientId);
        Clinician clinician = referralController.findClinicianById(clinicianId);
        Clinician toClinician = referralController.findClinicianById(clinicianId);
        Facility fromFacility = referralController.findFacilityById(fromFacilityId);
        Facility toFacility = referralController.findFacilityById(toFacilityId);

        Appointment appointment = null;
        if (!appointmentId.isBlank() && !appointmentId.equalsIgnoreCase("N/A")) {
            appointment = referralController.findAppointmentById(appointmentId);
        }

        if (patient == null || clinician == null || fromFacility == null || toFacility == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid referral data detected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Referral referral = new Referral(
                referralId,
                patient,
                clinician,
                toClinician,
                fromFacility,
                toFacility,
                referralDate,
                urgency,
                referralReason,
                clinicalSummary,
                investigations,
                status,
                appointment,
                notes,
                LocalDate.now(),     // created date (kept simple)
                LocalDate.now()      // last updated
        );

        new addReferralUI(this, referralController, referral, row).setVisible(true);
    }

    public void updateReferralInTable(int row, Referral r) {

        model.setValueAt(r.getReferralId(), row, 0);
        model.setValueAt(r.getPatient().getFullName(), row, 1);
        model.setValueAt(r.getReferringClinician().getClinicianId(), row, 2);
        model.setValueAt(r.getReferredToClinicianId(), row, 3);
        model.setValueAt(r.getReferringFacilityId(), row, 4);
        model.setValueAt(r.getReferredToFacilityId(), row, 5);
        model.setValueAt(r.getReferralDate(), row, 6);
        model.setValueAt(r.getUrgencyLevel(),row, 7);
        model.setValueAt(r.getReferralReason(), row, 8);
        model.setValueAt(r.getClinicalSummary(), row, 9);
        model.setValueAt(r.getRequestedInvestigations(), row, 10);
        model.setValueAt(r.getStatus(), row, 11);

        if (r.getAppointment() != null) {
            model.setValueAt(r.getAppointment().getAppointmentId(), row, 12);
        } else {
            model.setValueAt("N/A", row, 12);
        }

        model.setValueAt(r.getNotes(), row, 13);
        model.setValueAt(r.getCreatedDate(), row, 14);
        model.setValueAt(r.getLastUpdated(), row, 15);
    }

    private void deleteSelectedReferral() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a referral to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this referral?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(row);
        }
    }







}
