package com.example.sparespark.model;

public class AllUserRoleModel {
    String name , phoneno , uid;

    public  AllUserRoleModel(String name , String phoneno , String uid ){
        this.name = name;
        this.phoneno = phoneno;
        this.uid = uid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
