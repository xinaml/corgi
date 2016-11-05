package com.dounine.corgi.spring.rpc;

import java.lang.annotation.*;

/**
 * Created by huanghuanlai on 16/10/10.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String version() default "1.0.0";
    int timeout() default 3000;
    String url() default "";
}