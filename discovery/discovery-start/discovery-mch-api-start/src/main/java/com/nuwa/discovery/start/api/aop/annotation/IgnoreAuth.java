package com.nuwa.discovery.start.api.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IgnoreAuth 忽略验证
 *
 * @author hy
 * @date 2020/9/8 15:38
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreAuth {
}
