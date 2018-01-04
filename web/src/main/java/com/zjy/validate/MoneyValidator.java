package com.zjy.validate;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MoneyValidator implements ConstraintValidator<Money, Double> {
	 

    private Pattern moneyPattern;
   
    @Override
    public void initialize(Money money) {
       moneyPattern = Pattern.compile(money.value());
    }
 
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext arg1) {
       // TODO Auto-generated method stub
       if (value == null)
           return true;
       return moneyPattern.matcher(value.toString()).matches();
    }

}
