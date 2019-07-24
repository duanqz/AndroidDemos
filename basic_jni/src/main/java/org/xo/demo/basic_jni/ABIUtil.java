package org.xo.demo.basic_jni;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.Field;

/**
 * Utility to get ABI(Application Binary Interface) of a runtime package
 */
public class ABIUtil {

    public static String getABI(PackageManager pm, String packageName) {
        try {
            ApplicationInfo ai = pm.getApplicationInfo(
                    packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES);
            return getABI(ai);
        } catch (PackageManager.NameNotFoundException e) {
            // Fall through
        }

        return null;
    }

    public static String getABI(ApplicationInfo ai) {
        Object primaryCpuAbi = getField(ai, "primaryCpuAbi");
        if (primaryCpuAbi != null) {
            return (String) primaryCpuAbi;
        } else {
            return parseABIFromNativeLibraryDir(ai);
        }
    }

    private static Object getField(Object object, String filedName) {
        try {
            Field f = object.getClass().getDeclaredField(filedName);
            f.setAccessible(true);
            return f.get(object);
        } catch (NoSuchFieldException e) {
            // Fall through
        } catch (IllegalAccessException e) {
            // Fall through
        }

        return null;
    }

    private static String parseABIFromNativeLibraryDir(ApplicationInfo ai) {
        final String absolutePath = ai.nativeLibraryDir;
        final String basename = absolutePath.substring(absolutePath.lastIndexOf("/") + 1);
        switch (basename) {
            case "arm":
                return "armeabi-v7a";
            case "arm64":
                return "arm64-v8a";
            default:
                return Build.SUPPORTED_ABIS[0];
        }
    }
}