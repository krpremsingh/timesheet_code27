/*package com.awcsoftware.validation.test;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;

import com.awcsoftware.app.timesheet.TimeCardDetails;

public class ValidatorTest {
public static void main(String[] args) {
	ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	Validator validator = validatorFactory.getValidator();
	
	//If there are multiple JSR303 implementations in classpath
	//we can get HibernateValidator specifically too
	ValidatorFactory hibernateVF = Validation.byProvider(HibernateValidator.class)
								.configure().buildValidatorFactory();
	System.out.println("\nSimple field level validation example");
	TimeCardDetails emp = new TimeCardDetails(null,"90",null,"","");
	Set<ConstraintViolation<TimeCardDetails>> validationErrors = validator.validate(emp);
	
	if(!validationErrors.isEmpty()){
		for(ConstraintViolation<TimeCardDetails> error : validationErrors){
			System.out.println(error.getPropertyPath()+"::"+error.getMessage());
			
		}
}
}
}
*/