package org.xo.demo.exception_catcher.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CatcherGroup {

    /**
     * Define a list of {@link Catcher} for the target
     */
    Catcher[] catchers();
}
