object Versions {

    object BuildConfig {
        const val compileSdkVersion = 31
        const val minSdkVersion = 23
        const val targetSdkVersion = 31
        const val appVersionCode = 1
        const val appVersionName = "1.0.0"
    }

    object Plugin {
        const val androidGradlePluginVersion = "7.1.0-alpha13"
        const val navigationGradlePluginVersion = "2.4.0-alpha10"
        const val hiltGradlePluginVersion = "2.39.1"
        const val versionsPluginVersion = "0.39.0"
    }

    object Kotlin {
        const val kotlinVersion = "1.5.31"
        const val coroutinesVersion = "1.5.2"
        const val ktxVersion = "1.7.0-rc01"
        const val kotlinSerializationVersion = "1.3.0"
    }

    object Android {
        const val appCompatVersion = "1.4.0-beta01"
        const val constraintLayoutVersion = "2.1.1"
        const val archLifeCycleViewModelVersion = "2.4.0-rc01"
        const val lifecycleCommonVersion = "2.4.0-rc01"
        const val navigationVersion = Plugin.navigationGradlePluginVersion
    }

    object Material {
        const val materialVersion = "1.5.0-alpha04"
    }

    object Hilt {
        const val daggerHiltAndroidVersion = Plugin.hiltGradlePluginVersion
        const val daggerHiltAndroidCompilerVersion = Plugin.hiltGradlePluginVersion
        const val androidxHiltCompilerVersion = "1.0.0"
    }

    object Network {
        const val okHttpVersion = "4.9.0"
        const val okHttpInterceptorVersion = "4.9.0"
        const val okIOVersion = "2.10.0"
        const val retrofitVersion = "2.9.0"
        const val retrofitAdapterVersion = "0.9.2"
        const val retrofitSerializationConverterVersion = "0.8.0"
    }

    object Logging {
        const val timberVersion = "5.0.1"
    }

    object Test {
        const val jUnitVersion = "4.13.2"
        const val androidJUnitVersion = "1.1.4-alpha03"
        const val espressoCoreVersion = "3.5.0-alpha03"
        const val coreTestingVersion = "2.1.0"
        const val coroutinesTestVersion = "1.4.3"
        const val mockkVersion = "1.11.0"
    }
}
