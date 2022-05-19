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



-keep class com.example.firebasenotification.** {*;}
-keep class com.android.**
-keep class com.squareup.**
-keep class com.google.**
-keep class kotlin.**
-keep class kotlin.Metadata.** {*;}
-keep class retrofit2.**{*;}
-keepclasseswithmembers class * { @retrofit2.http.* <methods>;}
-keep class android.app.**
-keep class android.**
-keep class androidx.**
-keep class com.google.gson.stream.**{*;}
-keep class com.google.firebase.** {*;}
-keep class com.squareup.okhttp3.** {*;}
-keep interface com.squareup.okhttp3.**{*;}
-keep @kotlinx.android.parcel.Parcelize public class *

-keepclasseswithmembernames class * {
native<methods>;
}
#-keep interface kotlin.**
#-keep interface com.google.**
#-keep interface com.example.firebasenotification.**
#-keep interface com.squareup.**
#-keep interface com.android.**




-keepclassmembers class com.example.firebasenotification.sqlLite.UserModel** {<fields>;}
-keepclassmembers class com.example.firebasenotification.objectDatabase.DataModel**{<fields>;}
-keepclassmembers class com.example.firebasenotification.databinding.recyclerviewDatabinding.Model**{<fields>;}
-keepclassmembers class com.example.firebasenotification.androidTask.Songs**{<fields>;}
-keep class com.example.firebasenotification.sqlLite.UserModel.**{*;}
-keep class com.example.firebasenotification.objectDatabase.DataModel.**{*;}
-keep class com.example.firebasenotification.databinding.recyclerviewDatabinding.Model.**{*;}
-keep class com.example.firebasenotification.androidTask.Songs.**{*;}


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
-dontwarn google-services.json
-dontwarn objectbox-models
-dontwarn com.example.firebasenotification.**
-dontwarn com.android.**
-dontwarn com.squareup.**
-dontwarn com.google.**
-dontwarn kotlin.Metadata.**
-dontwarn retrofit.**
-dontwarn okio.**
-dontwarn rx.**
-dontwarn retrofit.appengine.UrlFetchClient
-dontwarn com.example.firebasenotification.adapter.**
-dontwarn javax.**

-printmapping mapping.txt
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable












#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#-keepclassmembers class **.R$* {
#    public static <fields>;
#}


