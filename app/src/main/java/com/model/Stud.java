package com.model;

/**
 * Created by krishan on 12/7/17.
 */

public class Stud {


    private String name;
    private String roll;
    private String age;
    private String row;

    public Stud(String name, String roll, String age, String row) {
        this.name = name;
        this.roll = roll;
        this.age = age;
        this.row = row;
    }
    public Stud() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }
}
