// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Depends.BuildPlugins.androidPlugin)
        classpath(Depends.BuildPlugins.kotlinPlugin)
        classpath(Depends.BuildPlugins.kotlinSerializationPlugin)
        classpath(Depends.BuildPlugins.navigationPlugin)
        classpath(Depends.BuildPlugins.hiltPlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
