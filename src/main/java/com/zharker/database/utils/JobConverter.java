package com.zharker.database.utils;

import com.zharker.database.domain.Job;

import javax.persistence.AttributeConverter;
import java.util.Arrays;

public class JobConverter implements AttributeConverter<Job[],String[]> {
    @Override
    public String[] convertToDatabaseColumn(Job[] jobs) {
        if(jobs == null){
            return null;
        }
        return Arrays.stream(jobs).map(Job::getTitle).toArray(String[]::new);
    }

    @Override
    public Job[] convertToEntityAttribute(String[] titles) {
        if(titles == null){
            return null;
        }
        return Arrays.stream(titles).map(Job::fromTitle).toArray(Job[]::new);
    }
}
