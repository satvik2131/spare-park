package com.example.sparespark.model;

public class LocationModel {

    String tenant_name;
    String address;
    String phoneno;
    String timing ;
    double latitude;
    double longitude;
    boolean status ;

    public LocationModel(String tenant_name , String address,
                         String phoneno, String timing ,
                         double latitude , double longitude
    ){
        this.tenant_name = tenant_name;
        this.address = address;
        this.phoneno = phoneno;
        this.timing = timing;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = false;
    }


    //Getter Setter

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
