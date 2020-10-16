package com.lhv.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVField {

  String name() default "";

  CSVColumnDataType dataType() default CSVColumnDataType.STRING;

  String dateFormat() default "mm/dd/yyyy";
}
