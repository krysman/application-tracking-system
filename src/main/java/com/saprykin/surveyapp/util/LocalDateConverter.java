package com.saprykin.surveyapp.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Converter to convert from java.time.LocalDate to java.sql.Date and back.
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {

        if(null == localDate){
            return null;
        } else {
            return Date.valueOf(localDate);
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {

        if(null == date) {
            return null;
        } else {
            return date.toLocalDate();
        }
    }

}