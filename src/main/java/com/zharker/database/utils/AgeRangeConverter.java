package com.zharker.database.utils;

import com.zharker.database.domain.AgeRange;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AgeRangeConverter implements AttributeConverter<AgeRange,String> {
    @Override
    public String convertToDatabaseColumn(AgeRange ageRange) {
        return ageRange.getAge()+"_"+ageRange.name().toUpperCase();
    }

    @Override
    public AgeRange convertToEntityAttribute(String s) {
        if(StringUtils.isEmpty(s)){
            return null;
        }
        return AgeRange.setAgeRange(Integer.valueOf(s.substring(0,s.indexOf('_'))));
    }
}
