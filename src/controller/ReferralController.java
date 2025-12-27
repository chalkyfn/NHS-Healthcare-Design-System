package controller;

import data.DataLoader;
import data.DataSaver;
import models.*;

import java.util.List;

public class ReferralController {

    public List<Referral> getAllReferrals(
            List<Patient> patients,
            List<Clinician> clinicians,
            List<Facility> facilities,
            List<Appointment> appointments
    ) {
        return DataLoader.loadReferrals(
                "data/referrals.csv",
                patients,
                clinicians,
                facilities,
                appointments
        );
    }

    public void exportReferral(Referral referral) {
        DataSaver.saveReferralText(referral);
    }

}
