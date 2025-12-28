package ui;

import controller.ReferralController;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class addReferralUI extends JFrame {

    private ReferralUI parent;
    private ReferralController referralController;

    private Referral editingReferral;
    private int editingRow = -1;

    // Fields
    private JTextField referralIdField;
    private JTextField patientIdField;
    private JTextField referringClinicianIdField;
    private JTextField referredToField;
    private JTextField fromFacilityIdField;
    private JTextField toFacilityIdField;
    private JTextField referralDateField;
    private JTextField urgencyField;
    private JTextField referralReasonField;
    private JTextField clinicalSummaryField;
    private JTextField investigationsField;
    private JTextField statusField;
    private JTextField appointmentIdField;
    private JTextField notesField;

    //ADD constructor
    public addReferralUI(ReferralUI parent, ReferralController controller) {
        this.parent = parent;
        this.referralController = controller;

        setTitle("Add Referral");
        setSize(600, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }


       // EDIT constructor
    public addReferralUI(
            ReferralUI parent,
            ReferralController controller,
            Referral referral,
            int row
    ) {
        this.parent = parent;
        this.referralController = controller;
        this.editingReferral = referral;
        this.editingRow = row;

        setTitle("Edit Referral");
        setSize(600, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        fillFields(referral);
    }

    /* =========================
       UI
       ========================= */
    private void initUI() {

        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        referralIdField = new JTextField();
        patientIdField = new JTextField();
        referringClinicianIdField = new JTextField();
        referredToField = new JTextField();
        fromFacilityIdField = new JTextField();
        toFacilityIdField = new JTextField();
        referralDateField = new JTextField(LocalDate.now().toString());
        urgencyField = new JTextField();
        referralReasonField = new JTextField();
        clinicalSummaryField = new JTextField();
        investigationsField = new JTextField();
        statusField = new JTextField();
        appointmentIdField = new JTextField();
        notesField = new JTextField();

        panel.add(new JLabel("Referral ID:"));
        panel.add(referralIdField);

        panel.add(new JLabel("Patient ID:"));
        panel.add(patientIdField);

        panel.add(new JLabel("Referring Clinician ID:"));
        panel.add(referringClinicianIdField);

        panel.add(new JLabel("Referred To:"));
        panel.add(referredToField);

        panel.add(new JLabel("From Facility ID:"));
        panel.add(fromFacilityIdField);

        panel.add(new JLabel("To Facility ID:"));
        panel.add(toFacilityIdField);

        panel.add(new JLabel("Referral Date (YYYY-MM-DD):"));
        panel.add(referralDateField);

        panel.add(new JLabel("Urgency:"));
        panel.add(urgencyField);

        panel.add(new JLabel("Referral Reason:"));
        panel.add(referralReasonField);

        panel.add(new JLabel("Clinical Summary:"));
        panel.add(clinicalSummaryField);

        panel.add(new JLabel("Requested Investigations:"));
        panel.add(investigationsField);

        panel.add(new JLabel("Status:"));
        panel.add(statusField);

        panel.add(new JLabel("Appointment ID (optional):"));
        panel.add(appointmentIdField);

        panel.add(new JLabel("Notes:"));
        panel.add(notesField);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton saveBtn = new JButton("Save Referral");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveReferral());
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /* =========================
       Fill fields (EDIT)
       ========================= */
    private void fillFields(Referral r) {

        referralIdField.setText(r.getReferralId());
        patientIdField.setText(r.getPatient().getPatientId());
        referringClinicianIdField.setText(r.getReferringClinician().getClinicianId());
        referredToField.setText(r.getReferredToClinicianId());
        fromFacilityIdField.setText(r.getReferringFacilityId());
        toFacilityIdField.setText(r.getReferredToFacilityId());
        referralDateField.setText(r.getReferralDate().toString());
        urgencyField.setText(r.getUrgencyLevel());
        referralReasonField.setText(r.getReferralReason());
        clinicalSummaryField.setText(r.getClinicalSummary());
        investigationsField.setText(r.getRequestedInvestigations());
        statusField.setText(r.getStatus());

        if (r.getAppointment() != null) {
            appointmentIdField.setText(r.getAppointment().getAppointmentId());
        }

        notesField.setText(r.getNotes());
    }

    private void saveReferral() {

        try {
            Referral referral = referralController.createReferral(
                    referralIdField.getText().trim(),
                    patientIdField.getText().trim(),
                    referringClinicianIdField.getText().trim(),
                    referredToField.getText().trim(),
                    fromFacilityIdField.getText().trim(),
                    toFacilityIdField.getText().trim(),
                    LocalDate.parse(referralDateField.getText().trim()),
                    urgencyField.getText().trim(),
                    referralReasonField.getText().trim(),
                    clinicalSummaryField.getText().trim(),
                    investigationsField.getText().trim(),
                    statusField.getText().trim(),
                    appointmentIdField.getText().trim(),
                    notesField.getText().trim()
            );

            if (editingReferral == null) {
                parent.addReferralToTable(referral);
            } else {
                parent.updateReferralInTable(editingRow, referral);
            }

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid input. Please check your data.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
