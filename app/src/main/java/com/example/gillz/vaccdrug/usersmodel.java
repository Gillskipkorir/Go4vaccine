package com.example.gillz.vaccdrug;


public class usersmodel {

    private String fname;
    private String lname;
    private int idno;
    private int phoneno;


    public usersmodel(String fname, String lname, int idno, int phoneno) {
        this.fname = fname;
        this.lname = lname;
        this.idno = idno;
        this.phoneno = phoneno;

    }

    

    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public int getIdno() {
        return idno;
    }
    public int getPhoneno() {
        return phoneno;
    }


}
