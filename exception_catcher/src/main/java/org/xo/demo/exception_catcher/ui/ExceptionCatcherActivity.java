package org.xo.demo.exception_catcher.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import org.xo.demo.core.ui.BaseActivityWithToolBar;
import org.xo.demo.exception_catcher.annotation.Catcher;
import org.xo.demo.exception_catcher.annotation.CatcherGroup;
import org.xo.demo.exception_catcher.interceptor.ExceptionInterceptor;

import java.lang.reflect.Method;

public class ExceptionCatcherActivity extends BaseActivityWithToolBar {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testCatcher();
        testCatcherGroup();
    }

    @Catcher
    private void testCatcher() {
        try {
            Method method = new Object(){ }.getClass().getEnclosingMethod();
            Catcher catcher = method.getAnnotation(Catcher.class);
            ExceptionInterceptor interceptor = catcher.targetExceptionInterceptor().newInstance();
            interceptor.onInterceptException(new Exception("BlabBla"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @CatcherGroup( catchers = {
            @Catcher(targetException = NoSuchMethodException.class),
            @Catcher(targetException = UnsatisfiedLinkError.class)
    })
    private void testCatcherGroup() {

    }
}
