package models;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

public class ReferralManager {
    private static  ReferralManager instance;
    private List<Referral> referrals = new ArrayList<>();

    private ReferralManager(){}

    public static ReferralManager getInstance(){
        if (instance == null){
            instance = new ReferralManager();
        }
        return  instance;
    }

    public void  addReferral(Referral r){
        referrals.add(r);
    }

    public Referral getReferralById(String id){
        return referrals.stream()
                .filter(r->r.getReferralId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
