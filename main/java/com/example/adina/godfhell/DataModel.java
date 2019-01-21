package com.example.adina.godfhell;

public class DataModel {

    String name;
    String rollno;
    String due;
    String mobile;


    public DataModel(String name, String rollno, String due, String mobile ) {
        this.name=name;
        this.rollno=rollno;
        this.due=due;
        this.mobile=mobile;

    }


    public String getName() {
        return name;
    }


    public String getType() {
        return rollno;
    }


    public String getVersion_number() {
        return due;
    }


    public String getFeature() {
        return mobile;
    }

}
