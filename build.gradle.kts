

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlinVersion by extra {"1.9.10"}
    val roomVersion by extra {"2.1.0-alpha01"}
    val navigationVersion by extra {"2.7.7"}
    val kodeinVersion by extra {"5.2.0"}
    val lifecycleVersion by extra {"2.0.0"}
    val retrofitVersion by extra {"2.4.0"}




    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "7.3.1" apply false
    id ("com.android.library") version "7.3.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.10" apply false
}
