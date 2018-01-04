package com.zjy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class CustomDateConverter implements Converter<String,Date>{

    @Override
    public Date convert(String source) {

        try {
        	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date result= simpleDateFormat.parse(source);
            return  result;
        } catch (ParseException e) {
            return null;
        }

    }

}
