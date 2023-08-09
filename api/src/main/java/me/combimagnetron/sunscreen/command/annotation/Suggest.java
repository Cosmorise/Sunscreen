package me.combimagnetron.sunscreen.command.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface Suggest {

    String registered() default "NaN";

    String[] primitive() default {};

}
