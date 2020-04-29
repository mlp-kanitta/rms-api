/**
 * 
 */
package com.rms.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Kanitta Moonlapong
 *
 */
public class EmployeeValidator implements ConstraintValidator<Employee, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		return true;

	}
}