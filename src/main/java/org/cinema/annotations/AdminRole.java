package org.cinema.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Inherited
@Documented
@Target(ElementType.METHOD)
@PreAuthorize("hasRole('ADMIN')")
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminRole {
}
