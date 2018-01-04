package com.zjy.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class MyWebBindingInitializer implements WebBindingInitializer {  
  
    @Override  
    public void initBinder(WebDataBinder binder, WebRequest request) {  
        // TODO Auto-generated method stub  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));  
    }  
  
}  