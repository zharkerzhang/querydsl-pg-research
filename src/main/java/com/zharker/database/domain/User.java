package com.zharker.database.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zharker.database.utils.AgeRangeConverter;
import com.zharker.database.utils.Constants;
import com.zharker.database.utils.JobConverter;
import com.zharker.database.utils.RawJsonDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user", schema = Constants.DB_SCHEMA, indexes = {@Index(columnList = "id", unique = true)})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends IdEntity {


    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;


    private String country;

    private Boolean editable;

    private String emails = "";

    private String externalRef;

//    private String gender;

    private String jobTitle;


    private String nationality;


    private String password;

    private String status;


    @Type(type = "string-array")
    @Column(
            name = "user_types",
            columnDefinition = "text[]"
    )
    private String[] userTypes;


    @Type(type = "int-array")
    @Column(
            name = "num_arr",
            columnDefinition = "int[]"
    )
    private Integer[] numArr;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    @JsonDeserialize(using = RawJsonDeserializer.class)
    private Serializable customizedAttributes;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private Integer age;

    @Column
    @Convert(converter = AgeRangeConverter.class)
    private AgeRange ageRange;

    public void setAge(int age){
        this.age = age;
        this.ageRange = AgeRange.setAgeRange(age);
    }

/*
    todo: implement enum and array
    @Column(
            name = "jobs",
            columnDefinition = "text[]"
    )
    @Convert(converter = JobConverter.class)
    private Job[] jobs;
*/

}
