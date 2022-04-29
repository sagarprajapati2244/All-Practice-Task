# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.example.firebasemessegingutils.** {*;}
-keep class com.android.** {*;}
-keep class com.facebook.** {*;}
-keep class com.google.** {*;}
-keep class kotlin.**
-keep class kotlin.Metadata.** {*;}
-keep class androidx.** {*;}
-keep class android.**
-keep class com.google.firebase.** {*;}
-keepclasseswithmembernames class * {
     public <methods>;
}

-keepattributes Signature
-keepattributes *Annotation*


-assumenosideeffects class android.util.Log{
    public static *** d(...);
    public static *** e(...);
}

-adaptclassstrings
-useuniqueclassmembernames
-allowaccessmodification

-dontwarn kotlin.**
-dontwarn google-service.json
-dontwarn com.ups.firebaselib.**
-dontwarn com.android.**
-dontwarn com.facebook.**
-dontwarn com.google.**
-dontwarn kotlin.Metadata.**
-dontwarn androidx.**
-dontwarn android.**

-printmapping mapping.txt
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable