package controller;

import models.Referral;
import models.Patient;
import models.Clinician;
import models.Facility;

import service.ReferralManager;

import java.time.LocalDate;

public class ReferralController {

    private final ReferralManager referralManager;

    public ReferralController() {
        this.referralManager = ReferralManager.getInstance();
    }

    public Referral createReferral(
            String referralId,
            Patient patient,
            Clinician referringClinician,
            Clinician referredToClinician,
            Facility fromFacility,
            Facility toFacility,
            String urgency,
            String reason,
            String clinicalSummary,
            String investigations) {

        Referral referral = new Referral(
                referralId,
                patient,
                referringClinician,
                referredToClinician,
                fromFacility,
                toFacility,
                LocalDate.now(),
                urgency,
                reason,
                clinicalSummary,
                investigations,
                "New",
                null,
                "",
                LocalDate.now(),
                LocalDate.now()
        );

        referralManager.processReferral(referral);
        return referral;
    }
}
