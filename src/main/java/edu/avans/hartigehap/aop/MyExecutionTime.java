package edu.avans.hartigehap.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("squid:S00112")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyExecutionTime {
    
}
