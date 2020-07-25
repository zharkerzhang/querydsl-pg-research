package com.zharker.database.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Job {

    Driver("driver"),
    Teacher("teacher"),
    Engineer("engineer"),
    Designer("designer"),
    Manager("manager"),
    Police("police"),
    Doctor("doctor");

    private String title;


    public static Job fromTitle(String title){
        switch (title){
            case "driver" : return Driver;
            case "teacher" : return Teacher;
            case "engineer" : return Engineer;
            case "designer" : return Designer;
            case "manager" : return Manager;
            case "police" : return Police;
            case "doctor" : return Doctor;
            default: return null;
        }
    }
}
