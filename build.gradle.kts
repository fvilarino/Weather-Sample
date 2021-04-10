// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val minSdkVersion by extra { 23 }
    val targetSdkVersion by extra { 30 }
    val compileSdkVersion by extra { 30 }

    val kotlinVersion by extra { "1.4.32" }
    val cityServiceBaseUrl by extra { "https://wft-geo-db.p.rapidapi.com/" }
    val weatherServiceBaseUrl by extra { "https://community-open-weather-map.p.rapidapi.com/" }
    val rapidServiceKey by extra { "your key here" }
    val rapidServiceCityHost by extra { "your host here" }
    val rapidServiceWeatherHost by extra { "your host here" }

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha14")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${rootProject.extra.get("kotlinVersion")}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${rootProject.extra.get("kotlinVersion")}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.33-beta")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
