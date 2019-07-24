#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_org_xo_demo_basic_1jni_BasicJniActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
