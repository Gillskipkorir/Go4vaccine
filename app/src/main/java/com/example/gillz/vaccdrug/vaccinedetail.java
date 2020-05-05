package com.example.gillz.vaccdrug;


public class vaccinedetail {

    private  String about;
    private  String vtype;
    private  String vusage;
    private  String precautions;


    public vaccinedetail( String about, String vtype, String vusage, String precautions) {

        this.about = about;
        this.vtype = vtype;
        this.vusage = vusage;
        this.precautions = precautions;

    }

    


    public  String about() {
        return about;
    }

    public  String vtype() {
        return vtype;
    }

    public  String vusage() {
        return vusage;
    }

    public  String precautions() {
        return precautions;
    }


}
