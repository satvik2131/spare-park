package com.example.sparespark.model;

public class UserBookRequest {
    String lrid;
    String tenant_name;
    String tenant_phoneno;
    String timing;
    String username;
    String phoneno;
    String tenant_address;
    boolean status;
    String uid;


    public UserBookRequest(){}


    public UserBookRequest(String lrid,
                           String tenant_name,
                           String tenant_phoneno,
                           String tenant_address,
                           String timing,
                           String username, String phoneno,
                           boolean status , String uid) {
        this.lrid = lrid;
        this.tenant_name = tenant_name;
        this.tenant_phoneno = tenant_phoneno;
        this.tenant_address = tenant_address;
        this.timing = timing;
        this.username = username;
        this.phoneno = phoneno;
        this.status = status;
        this.uid = uid;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getTenant_phoneno() {
        return tenant_phoneno;
    }

    public void setTenant_phoneno(String tenant_phoneno) {
        this.tenant_phoneno = tenant_phoneno;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public boolean isStatus() {
        return status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getTenant_address() {
        return tenant_address;
    }

    public void setTenant_address(String tenant_address) {
        this.tenant_address = tenant_address;
    }


    public String getLrid() {
        return lrid;
    }

    public void setLrid(String lrid) {
        this.lrid = lrid;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
