package controller;

import data.DataSaver;
import models.Prescription;
import models.Patient;
import models.Clinician;

import java.time.LocalDate;

public class PrescriptionController {

    public Prescription createPrescription(
            String prescriptionId,
            Patient patient,
            Clinician clinician,
            String medication,
            String dosage,
            String instructions,
            String pharmacy) {

        return new Prescription(
                prescriptionId,
                patient,
                clinician,
                medication,
                dosage,
                instructions,
                pharmacy,
                "Issued",
                LocalDate.now()
        );
    }


    // SAVE PRESCRIPTION AS TEXT FILE

    public void generatePrescriptionFile(
            Prescription prescription,
            String folderPath) {

        DataSaver.savePrescriptionText(prescription, folderPath);
    }


    // UPDATE PRESCRIPTION STATUS
    public void updateStatus(Prescription prescription, String newStatus) {
        prescription.updateStatus(newStatus);
    }
}
