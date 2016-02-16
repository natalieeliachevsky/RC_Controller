LOCAL_PATH := $(call my-dir)


include $(CLEAR_VARS)
LOCAL_MODULE    := prebuilt-libjpeg
LOCAL_SRC_FILES += libjpeg.so
LOCAL_SRC_FILES += libImagProc.so
#LOCAL_SRC_FILES := external/lib/libjpeg.so
include $(PREBUILT_SHARED_LIBRARY)


include $(CLEAR_VARS)
LOCAL_MODULE    := ImagProc
LOCAL_SRC_FILES := ImagProc.c
LOCAL_LDLIBS    := -llog -ljnigraphics
LOCAL_SHARED_LIBRARIES := prebuilt-libjpeg
LOCAL_C_INCLUDES       := external/include jni/external/include

include $(BUILD_SHARED_LIBRARY)

#LOCAL_MODULE := ImagProc
#LOCAL_SRC_FILES := ImagProc.c

#include $(BUILD_SHARED_LIBRARY)



