package com.rest.Security;

import org.ilay.NavigationAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NavigationAnnotation(EmailBasedEvaluator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecuredByEmail {
}
