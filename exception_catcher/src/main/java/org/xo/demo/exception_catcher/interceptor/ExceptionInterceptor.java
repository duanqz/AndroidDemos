package org.xo.demo.exception_catcher.interceptor;

public interface ExceptionInterceptor {

    void onInterceptException(Throwable throwable);
}
