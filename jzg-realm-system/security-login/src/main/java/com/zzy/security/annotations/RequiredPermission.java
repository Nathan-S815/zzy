package com.zzy.security.annotations;


import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {

    String hasPermission() default "";

    String hasRole() default "";

    String[] hasAnyRole() default "";

    String hasRoleCode() default "";




}
