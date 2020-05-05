package com.example.gillz.vaccdrug;


public class facilitymodel {

    private String facility;
    private String email;
    private String adress;
    private String street;
    private String town;
    private String openfrom;
    private String closedat;
    private int phone;
    private int paybill;


    public facilitymodel(String facility, int phone, String email, String adress, String street, String town, String openfrom, String closedat, int paybill) {

        this.facility = facility;
        this.phone = phone;
        this.email = email;
        this.adress = adress;
        this.street = street;
        this.town = town;
        this.openfrom = openfrom;
        this.closedat = closedat;
        this.paybill = paybill;


    }

    public String getFacility() {
        return facility;
    }
    public int getPhone () {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getAdress() {
        return adress;
    }
    public String getStreet() {
        return street;
    }
    public String getTown() {
        return town;
    }
    public String getOpenfrom() {
        return openfrom;
    }
    public String getClosedat() {
        return closedat;
    }
    public int getPaybill() {
        return paybill;
    }



}
