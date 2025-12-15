package controller;

import models.Prescription;
import models.Patient;
import models.Clinician;
import data.DataSaver;

import java.time.LocalDate;

public class PrescriptionController {

    public Prescription createPrescription(
            String id,
            Patient patient,
            Clinician clinician,
            String medication,
            String dosage,
            String instructions,
            String pharmacy) {

        Prescription prescription = new Prescription(
                id,
                patient,
                clinician,
                medication,
                dosage,
                instructions,
                pharmacy,
                "Issued",
                LocalDate.now()
        );

        DataSaver.savePrescriptionText(prescription,"data/prescription.txt");
        return prescription;
    }
}
