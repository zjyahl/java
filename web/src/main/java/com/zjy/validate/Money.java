package com.zjy.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=MoneyValidator.class)
public @interface Money {
	
	String value() default "^\\\\d+(\\\\.\\\\d{1,2})?$";
    String message() default"不是金额形式";
   
    Class<?>[] groups() default {};
   
    Class<? extends Payload>[] payload() default {};
 
}
