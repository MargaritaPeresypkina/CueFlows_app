plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.safeargs) apply false
    alias(libs.plugins.plagin.serialization) apply false
}

buildscript {
    repositories {
        mavenCentral()
    }
}