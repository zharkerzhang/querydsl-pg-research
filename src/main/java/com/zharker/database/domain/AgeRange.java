package com.zharker.database.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AgeRange {

    child(12),
    young(18),
    adult(30),
    middle(45),
    older(60),
    dying(100);

    private int age;

    public static AgeRange setAgeRange(int age){
        if(0<=age&&age<13){
            return child;
        } else if(13<=age&&age<19){
            return young;
        } else if(19<=age&&age<31){
            return adult;
        } else if(31<=age&&age<46){
            return middle;
        } else if(46<=age&&age<61){
            return older;
        } else if(61<=age&&age<101){
            return dying;
        } else{
            return null;
        }
    }
}
