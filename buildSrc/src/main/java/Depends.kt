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
        const val protoBuf = "com.google.protobuf"
        const val kotlinSerializationPlugin = "kotlinx-serialization"
        const val kotlinParcelizePlugin = "kotlin-parcelize"
        const val kotlinKapt = "kapt"
        const val versionsPlugin = "com.github.ben-manes.versions"
    }

    object Kotlin {
        const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Kotlin.kotlinSerializationVersion}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutinesVersion}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutinesVersion}"
    }

    object Android {
        const val ktx = "androidx.core:core-ktx:${Versions.Kotlin.ktxVersion}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.Android.appCompatVersion}"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.Android.navigationVersion}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.Android.navigationVersion}"
        const val archLifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Android.archLifeCycleViewModelVersion}"
        const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.Android.lifecycleCommonVersion}"
        const val dataStore = "androidx.datastore:datastore:${Versions.Android.dataStoreVersion}"
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

    object Google {
        const val protoBufJavaLite = "com.google.protobuf:protobuf-javalite:${Versions.Google.protoBufJavaLiteVersion}"
        const val protoBufProtoc = "com.google.protobuf:protoc:${Versions.Google.protoBufProtocVersion}"
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
