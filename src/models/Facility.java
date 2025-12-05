package models;

public class Facility {

    private String facilityID;
    private String facilityName;
    private String phoneNumber;
    private String facilityType;
    private int capacity;
    private String email;
    private String openingHours;
    private String address;
    private String postCode;
    private String managerName;
    private String[] specialitiesOffered;

    public Facility(String id, String name, String type, String address,
                    String postcode, String phone, String email,
                    String openingHours, String managerName,
                    int capacity, String[] specialities) {
        this.facilityID = id;
        this.facilityName = name;
        this.facilityType = type;
        this.address = address;
        this.postCode = postcode;
        this.phoneNumber = phone;
        this.email = email;
        this.openingHours = openingHours;
        this.managerName = managerName;
        this.capacity = capacity;
        this.specialitiesOffered = specialities;
    }

    public String getFacilityID(){
        return facilityID;
    }

    public String toString(){
        return facilityName + "(" + facilityType + ")";
    }
}
