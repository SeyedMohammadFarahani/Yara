# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/google/home/chrisbanes/bin/current-android-sdk/tools/proguard/proguard-android.txt
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

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-dontoptimize
-dontpreverify

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService

-renamesourcefileattribute SourceFile
-keepattributes  Signature,SourceFile,LineNumberTable
-keep public class * extends android.app.Application

-ignorewarnings
-keep class * {
    public private *;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class android.arch.persistence.room.**

-keepclassmembers class android.arch.persistence.room.** { *; }
-keep enum android.arch.persistence.room.**

-keepclassmembers enum android.arch.persistence.room.** { *; }
-keep interface android.arch.persistence.room.**

-keepclassmembers interface android.arch.persistence.room.** { *; }

###

-keep interface com.ansarbank.room.Database.** { *; }
-keep class com.ansarbank.room.Database.** { *; }
-dontwarn com.ansarbank.room.Database.**

-keep interface com.android.support.** { *; }
-keep class com.android.support.** { *; }
-dontwarn com.android.support.**

-keep interface com.tapadoo.android:alerter.** { *; }
-keep class com.tapadoo.android:alerter.** { *; }
-dontwarn com.tapadoo.android:alerter.**

-keep interface com.tapadoo.android.** { *; }
-keep class com.tapadoo.android.** { *; }
-dontwarn com.tapadoo.android.**

-keep interface org.jetbrains.kotlin.** { *; }
-keep class org.jetbrains.kotlin.** { *; }
-dontwarn org.jetbrains.kotlin.**

-keep interface com.github.bumptech.glide.** {*;}
-keep class com.github.bumptech.glide.** {*;}
-dontwarn com.github.bumptech.glide.**

-keep interface com.google.code.gson.** { *; }
-keep class com.google.code.gson.** { *; }
-dontwarn com.google.code.gson.**

-keep interface com.intuit.sdp.** { *; }
-keep class com.intuit.sdp.** { *; }
-dontwarn com.intuit.sdp.**

-keep interface com.intuit.ssp.** { *; }
-keep class com.intuit.ssp.** { *; }
-dontwarn com.intuit.ssp.**

-keep interface org.jetbrains.kotlin:kotlin-stdlib-jdk8.** { *; }
-keep class org.jetbrains.kotlin:kotlin-stdlib-jdk8.** { *; }
-dontwarn org.jetbrains.kotlin:kotlin-stdlib-jdk8.**

-keep interface com.alirezaafkar:toolbar.** { *; }
-keep class com.alirezaafkar:toolbar.** { *; }
-dontwarn com.alirezaafkar:toolbar.**

-keep interface com.android.support:design.** { *; }
-keep class com.android.support:design.** { *; }
-dontwarn com.android.support:design.**

-keep interface com.github.aliab:RTLMaterialSpinner.** { *; }
-keep class com.github.aliab:RTLMaterialSpinner.** { *; }
-dontwarn com.github.aliab:RTLMaterialSpinner.**

-keep interface com.github.sadra:AwesomeSpinner.** { *; }
-keep class com.github.sadra:AwesomeSpinner.** { *; }
-dontwarn com.github.sadra:AwesomeSpinner.**

-keep interface com.szagurskii:patternedtextwatcher.** { *; }
-keep class com.szagurskii:patternedtextwatcher.** { *; }
-dontwarn com.szagurskii:patternedtextwatcher.**

-keep interface me.dm7.barcodescanner:zxing.** { *; }
-keep class me.dm7.barcodescanner:zxing.** { *; }
-dontwarn me.dm7.barcodescanner:zxing.**

-keep interface com.android.support:support-v4.** { *; }
-keep class com.android.support:support-v4.** { *; }
-dontwarn com.android.support:support-v4.**