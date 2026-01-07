package com.globaljobsnepal.auth.config;

/**
 * @author prabesh on 03/10/24
 * @project jwt
 */

import java.lang.annotation.*;

@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredPermission {
    String[] value() default {};
}
