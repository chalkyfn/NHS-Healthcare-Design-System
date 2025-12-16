package controller;

import models.*;
import service.ReferralManager;

import java.time.LocalDate;
import java.util.List;

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
            List<String> investigations
    ) {

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
                "",
                LocalDate.now(),
                LocalDate.now(),
                null   // appointment not created yet
        );

        referralManager.processReferral(referral);
        return referral;
    }
}
