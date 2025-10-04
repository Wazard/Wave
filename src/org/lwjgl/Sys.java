/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.LinuxSysImplementation;
import org.lwjgl.MacOSXSysImplementation;
import org.lwjgl.SysImplementation;
import org.lwjgl.WindowsSysImplementation;
import org.lwjgl.input.Mouse;

public final class Sys {
    private static final String JNI_LIBRARY_NAME = "lwjgl";
    private static final String VERSION = "2.8.4";
    private static final String POSTFIX64BIT = "64";
    private static final SysImplementation implementation = Sys.createImplementation();
    private static final boolean is64Bit;

    private static void doLoadLibrary(final String lib_name) {
        AccessController.doPrivileged(new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                String library_path = System.getProperty("org.lwjgl.librarypath");
                if (library_path != null) {
                    System.load(library_path + File.separator + System.mapLibraryName(lib_name));
                } else {
                    System.loadLibrary(lib_name);
                }
                return null;
            }
        });
    }

    private static void loadLibrary(String lib_name) {
        boolean is64bit;
        String osArch = System.getProperty("os.arch");
        boolean bl = is64bit = "amd64".equals(osArch) || "x86_64".equals(osArch);
        if (is64bit) {
            try {
                Sys.doLoadLibrary(lib_name + POSTFIX64BIT);
                return;
            }
            catch (UnsatisfiedLinkError e) {
                LWJGLUtil.log("Failed to load 64 bit library: " + e.getMessage());
            }
        }
        try {
            Sys.doLoadLibrary(lib_name);
        }
        catch (UnsatisfiedLinkError e) {
            if (implementation.has64Bit()) {
                try {
                    Sys.doLoadLibrary(lib_name + POSTFIX64BIT);
                    return;
                }
                catch (UnsatisfiedLinkError e2) {
                    LWJGLUtil.log("Failed to load 64 bit library: " + e2.getMessage());
                }
            }
            throw e;
        }
    }

    private static SysImplementation createImplementation() {
        switch (LWJGLUtil.getPlatform()) {
            case 1: {
                return new LinuxSysImplementation();
            }
            case 3: {
                return new WindowsSysImplementation();
            }
            case 2: {
                return new MacOSXSysImplementation();
            }
        }
        throw new IllegalStateException("Unsupported platform");
    }

    private Sys() {
    }

    public static String getVersion() {
        return VERSION;
    }

    public static void initialize() {
    }

    public static boolean is64Bit() {
        return is64Bit;
    }

    public static long getTimerResolution() {
        return implementation.getTimerResolution();
    }

    public static long getTime() {
        return implementation.getTime() & Long.MAX_VALUE;
    }

    public static void alert(String title, String message) {
        boolean grabbed = Mouse.isGrabbed();
        if (grabbed) {
            Mouse.setGrabbed(false);
        }
        if (title == null) {
            title = "";
        }
        if (message == null) {
            message = "";
        }
        implementation.alert(title, message);
        if (grabbed) {
            Mouse.setGrabbed(true);
        }
    }

    public static boolean openURL(String url) {
        try {
            final Class<?> serviceManagerClass = Class.forName("javax.jnlp.ServiceManager");
            Method lookupMethod = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return serviceManagerClass.getMethod("lookup", String.class);
                }
            });
            Object basicService = lookupMethod.invoke(serviceManagerClass, "javax.jnlp.BasicService");
            final Class<?> basicServiceClass = Class.forName("javax.jnlp.BasicService");
            Method showDocumentMethod = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return basicServiceClass.getMethod("showDocument", URL.class);
                }
            });
            try {
                Boolean ret = (Boolean)showDocumentMethod.invoke(basicService, new URL(url));
                return ret;
            }
            catch (MalformedURLException e) {
                e.printStackTrace(System.err);
                return false;
            }
        }
        catch (Exception ue) {
            return implementation.openURL(url);
        }
    }

    public static String getClipboard() {
        return implementation.getClipboard();
    }

    static {
        Sys.loadLibrary(JNI_LIBRARY_NAME);
        is64Bit = implementation.getPointerSize() == 8;
        int native_jni_version = implementation.getJNIVersion();
        int required_version = implementation.getRequiredJNIVersion();
        if (native_jni_version != required_version) {
            throw new LinkageError("Version mismatch: jar version is '" + required_version + "', native library version is '" + native_jni_version + "'");
        }
        implementation.setDebug(LWJGLUtil.DEBUG);
    }
}

