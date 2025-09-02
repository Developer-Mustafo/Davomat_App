#include <jni.h>
#include <string>
#include <vector>
using namespace std;

string getApiUrl() {
    return "https://davomatbackend-production.up.railway.app";
}

extern "C" JNIEXPORT jstring
Java_uz_coder_davomatapp_native_getApiUrl(JNIEnv* env, jobject /* this */) {
    return env->NewStringUTF(getApiUrl().c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_uz_coder_davomatapp_network_ApiClient_getApiBaseUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(getApiUrl().c_str());
}