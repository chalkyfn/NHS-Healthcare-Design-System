package service;

import models.Referral;
import data.DataSaver;

import java.util.ArrayList;
import java.util.List;

public class ReferralManager {

    // ---------------- SINGLETON ----------------

    private static ReferralManager instance;

    private ReferralManager() {
        referralQueue = new ArrayList<>();
    }

    public static ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    // ---------------- DATA ----------------

    private List<Referral> referralQueue;

    // ---------------- BUSINESS LOGIC ----------------

    public void processReferral(Referral referral) {
        if (!referralQueue.contains(referral)) {
            referralQueue.add(referral);

            // Simulate sending referral (text output)
            DataSaver.saveReferralText(
                    referral,
                    "data/referrals_output.txt"
            );
        }
    }

    public List<Referral> getAllReferrals() {
        return referralQueue;
    }

    public Referral getReferralById(String referralId) {
        for (Referral r : referralQueue) {
            if (r.getReferralId().equals(referralId)) {
                return r;
            }
        }
        return null;
    }
}
