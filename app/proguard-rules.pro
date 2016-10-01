# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/akarbowy/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#RETROFIT
-dontwarn retrofit2.**
-dontwarn org.codehaus.mojo.**
-keep class retrofit2.** { *; }
-keepattributes *Annotation*

-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

-keepattributes EnclosingMethod

-keepclasseswithmembers class * {
    @retrofit2.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

#DAGGER
    -dontwarn dagger.internal.codegen.**
    -keepclassmembers,allowobfuscation class * {
        @javax.inject.* *;
        @dagger.* *;
        <init>();
    }
    -keep class dagger.* { *; }
    -keep class javax.inject.* { *; }
    -keep class * extends dagger.internal.Binding
    -keep class * extends dagger.internal.ModuleAdapter
    -keep class * extends dagger.internal.StaticInjection

-keep class com.j256.** { *; }
-keep interface com.j256.** { *; }
-dontwarn com.j256.**