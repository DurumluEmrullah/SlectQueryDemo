package com.company.annotations.dto;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTable {

    String leftTableName();
    String leftTableAlias() default "L";
    String rightTableName();
    String rightTableAlias() default "R";

}
