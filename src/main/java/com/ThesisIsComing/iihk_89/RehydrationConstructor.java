package com.ThesisIsComing.iihk_89;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface RehydrationConstructor {
}
 // spaeter mit ArchUnit -> absichern