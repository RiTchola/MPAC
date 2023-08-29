package org.rina.util.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import org.rina.util.validation.DatesCompareValidator;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DatesCompareValidator.class)
@Documented
public @interface DatesPastAndFutureValidation {
	String message() default "{org.rina.util.validation.datesCompare}";

	  Class<?>[] groups() default {};

	  Class<? extends Payload>[] payload() default {};

	  String d1();

	  String d2();
}
