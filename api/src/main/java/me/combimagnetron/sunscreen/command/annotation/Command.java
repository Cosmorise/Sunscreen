package me.combimagnetron.sunscreen.command.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String command();

    String[] aliases() default {};

}
