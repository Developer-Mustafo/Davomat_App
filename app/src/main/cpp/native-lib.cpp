#include <jni.h>
#include <string>
#include <vector>
using namespace std;

string getApiUrl() {
    return "http://192.168.1.150:8080";
}

extern "C" [[maybe_unused]] JNIEXPORT jstring
Java_uz_coder_davomatapp_native_getApiUrl(JNIEnv* env, jobject /* this */) {
    return env->NewStringUTF(getApiUrl().c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_uz_coder_davomatapp_data_network_ApiClient_getApiBaseUrl(JNIEnv *env, [[maybe_unused]] jclass clazz) {
    return env->NewStringUTF(getApiUrl().c_str());
}