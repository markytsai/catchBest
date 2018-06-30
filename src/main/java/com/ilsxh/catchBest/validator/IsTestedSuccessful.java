package com.ilsxh.catchBest.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @description
 * @author Caizhenya mail:tsaizhenya@gmail.com
 * @date 2018年6月30日 上午9:31:21
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { IsMobileSuccessValidator.class })
public @interface IsTestedSuccessful {
	// 默认不为空
	boolean required() default true;

	// 一下三个是必须的
	String message() default "手机号码格式错误";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
