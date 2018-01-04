package com.zjy.exception;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionHandler {
	
	@ExceptionHandler(BindException.class)
    public String validExceptionHandler(BindException e, WebRequest request, HttpServletResponse response) {

        List<FieldError> fieldErrors=e.getBindingResult().getFieldErrors();
        for (FieldError error:fieldErrors){
            System.out.println(error.getField()+":"+error.getDefaultMessage());
        }
        request.setAttribute("fieldErrors",fieldErrors,WebRequest.SCOPE_REQUEST);
       

        return "/validError";
    }

	@ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex){
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex);
        System.out.println("in testControllerAdvice");
        return mv;
    }

}
