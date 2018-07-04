package com.zzk.util;

import java.lang.annotation.*;

/**
 * 日志切面注解
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodAnnotation {

    String remark() default "";
    String operType() default "0";
}