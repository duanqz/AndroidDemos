package org.xo.demo.exception_catcher.annotation;

import org.xo.demo.exception_catcher.interceptor.DefaultExceptionInterceptor;
import org.xo.demo.exception_catcher.interceptor.ExceptionInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Catcher {

    /**
     * Set exception interceptor of the target.
     * The interceptor will be notified while catching an exception
     */
    Class<? extends ExceptionInterceptor> targetExceptionInterceptor() default DefaultExceptionInterceptor.class;

    /**
     * Set exception class of the target.
     * Define which exception is concerned.
     */
    Class<? extends Throwable> targetException() default Exception.class;
}
