package com.ilsxh.catchBest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @description
 * @author Caizhenya mail:tsaizhenya@gmail.com
 * @date 2018年6月30日 上午9:32:30
 *
 */
public class IsMobileSuccessValidator implements ConstraintValidator<IsMobile, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ("17788746288".equals(value)) {
			return true;
		}
		return false;
		
	}

}
