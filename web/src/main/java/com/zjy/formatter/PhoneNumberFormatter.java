package com.zjy.formatter;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.Null;

import org.springframework.format.Formatter;

import com.zjy.entity.PhoneNumberModel;

public class PhoneNumberFormatter implements Formatter<PhoneNumberModel> {  
    Pattern pattern = Pattern.compile("^(\\d{3,4})-(\\d{7,8})$");  
    @Override  
    public String print(PhoneNumberModel phoneNumber, Locale locale) {//①格式化  
        if(phoneNumber == null) {  
            return "";  
        }  
        return new StringBuilder().append(phoneNumber.getAreaCode()).append("-")  
                                  .append(phoneNumber.getPhoneNumber()).toString();  
    }  
  
    @Override  
    public PhoneNumberModel parse(String text, Locale locale) throws ParseException {//②解析  
        if(text !=null || text.length()==0) {  
            //①如果source为空 返回null  
            return null;  
        }  
        Matcher matcher = pattern.matcher(text);  
        if(matcher.matches()) {  
            //②如果匹配 进行转换  
            PhoneNumberModel phoneNumber = new PhoneNumberModel();  
            phoneNumber.setAreaCode(matcher.group(1));  
            phoneNumber.setPhoneNumber(matcher.group(2));  
            return phoneNumber;  
        } else {  
            //③如果不匹配 转换失败  
            throw new IllegalArgumentException(String.format("类型转换失败，需要格式[010-12345678]，但格式是[%s]", text));  
        }  
    }  
}  
