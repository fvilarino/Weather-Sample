object Depends {

    object BuildPlugins {
        const val androidPlugin = "com.android.tools.build:gradle:${Versions.Plugin.androidGradlePluginVersion}"
        const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.kotlinVersion}"
        const val kotlinSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:${Versions.Kotlin.kotlinVersion}"
        const val navigationPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Plugin.navigationGradlePluginVersion}"
        const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Plugin.hiltGradlePluginVersion}"
    }

    object ModulePlugins {
        const val applicationPlugin = "com.android.application"
        const val libraryPlugin = "com.android.library"
        const val kotlinPlugin = "kotlin-android"
        const val daggerHiltPlugin = "dagger.hilt.android.plugin"
        const val kotlinSerializationPlugin = "kotlinx-serialization"
        const val kotlinParcelizePlugin = "kotlin-parcelize"
        const val safeArgsPlugin = "androidx.navigation.safeargs.kotlin"
        const val kotlinKapt = "kapt"
        const val versionsPlugin = "com.github.ben-manes.versions"
    }

    object Kotlin {
        const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Kotlin.kotlinSerializationVersion}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutinesVersion}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutinesVersion}"
        // TODO: remove when compose moves from 0.3.3
        const val collectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:${Versions.Kotlin.collectionsImmutable}"
    }

    object Android {
        const val ktx = "androidx.core:core-ktx:${Versions.Kotlin.ktxVersion}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.Android.appCompatVersion}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.Android.constraintLayoutVersion}"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.Android.navigationVersion}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.Android.navigationVersion}"
        const val archLifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Android.archLifeCycleViewModelVersion}"
        const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.Android.lifecycleCommonVersion}"
    }

    object Compose {
        const val composeUi =  "androidx.compose.ui:ui:${Versions.Compose.composeVersion}"
        const val composeMaterial = "androidx.compose.material:material:${Versions.Compose.composeVersion}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.Compose.composeVersion}"
        const val activityCompose =  "androidx.activity:activity-compose:${Versions.Compose.activityCompose}"
        const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.Compose.navigationCompose}"
    }

    object Hilt {
        const val daggerHiltAndroid = "com.google.dagger:hilt-android:${Versions.Hilt.daggerHiltAndroidVersion}"
        const val daggerHiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Hilt.daggerHiltAndroidCompilerVersion}"
        const val androidxHiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.Hilt.androidxHiltNavigation}"
        const val androidxHiltCompiler = "androidx.hilt:hilt-compiler:${Versions.Hilt.androidxHiltCompilerVersion}"
    }

    object Network {
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.Network.okHttpVersion}"
        const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.Network.okHttpInterceptorVersion}"
        const val okIO = "com.squareup.okio:okio:${Versions.Network.okIOVersion}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.Network.retrofitVersion}"
        const val retrofitAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.Network.retrofitAdapterVersion}"
        const val retrofitSerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.Network.retrofitSerializationConverterVersion}"
    }

    object Logging {
        const val timber = "com.jakewharton.timber:timber:${Versions.Logging.timberVersion}"
    }

    object Material {
        const val material = "com.google.android.material:material:${Versions.Material.materialVersion}"
    }

    object TestLibraries {
        const val jUnit = "junit:junit:${Versions.Test.jUnitVersion}"
        const val androidJUnit = "androidx.test.ext:junit:${Versions.Test.androidJUnitVersion}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.Test.espressoCoreVersion}"
        const val coreTesting = "androidx.arch.core:core-testing:${Versions.Test.coreTestingVersion}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Test.coroutinesTestVersion}"
        const val mockk = "io.mockk:mockk:${Versions.Test.mockkVersion}"
        const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}
