package com.rest.Security;

import org.ilay.NavigationAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NavigationAnnotation(NameBasedEvaluator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecuredForIllia {
}