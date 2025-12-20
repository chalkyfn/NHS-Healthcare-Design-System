package controller;

import data.DataLoader;
import data.DataSaver;
import models.*;

import java.util.List;

public class PrescriptionController {

    public List<Prescription> getAllPrescriptions(
            String filePath,
            List<Patient> patients,
            List<Clinician> clinicians
    ) {
        return DataLoader.loadPrescriptions(filePath, patients, clinicians);
    }

    public void generatePrescriptionFile(
            Prescription prescription,
            String folderPath
    ) {
        DataSaver.savePrescriptionText(prescription, folderPath);
    }

    public void updateStatus(Prescription prescription, String newStatus) {
        prescription.updateStatus(newStatus);
    }
}
