package com.ilsxh.catchBest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.ilsxh.catchBest.util.ValidatorUtil;

/**
 * 需要继承ConstraintValidator，注解名称，注解内容类型
 * @author Caizhenya
 *
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
 
	private boolean required = false;

	public void initialize(IsMobile constraintAnnotation) {
		// 拿到注解是不是必须的
		required = constraintAnnotation.required();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (required) {
			return ValidatorUtil.isMobile(value);
		} else {
			if (StringUtils.isEmpty(value)) {
				return true;
			} else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
