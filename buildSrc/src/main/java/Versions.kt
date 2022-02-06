object Versions {

    object BuildConfig {
        const val compileSdkVersion = 31
        const val minSdkVersion = 23
        const val targetSdkVersion = 31
        const val appVersionCode = 1
        const val appVersionName = "1.0.1"
    }

    object Plugin {
        const val androidGradlePluginVersion = "7.3.0-alpha01"
        const val navigationGradlePluginVersion = "2.5.0-alpha01"
        const val hiltGradlePluginVersion = "2.40.5"
        const val protobufPluginVersion = "0.8.12"
        const val versionsPluginVersion = "0.42.0"
    }

    object Compose {
        const val composeCompilerVersion = "1.1.0-rc02"
        const val composeVersion = "1.1.0-rc01"
        const val activityCompose = "1.3.0-alpha06"
        const val navigationCompose = Plugin.navigationGradlePluginVersion
    }

    object Kotlin {
        const val kotlinVersion = "1.6.10"
        const val coroutinesVersion = "1.6.0"
        const val ktxVersion = "1.8.0-alpha03"
        const val kotlinSerializationVersion = "1.3.2"
    }

    object Android {
        const val appCompatVersion = "1.4.1"
        const val archLifeCycleViewModelVersion = "2.5.0-alpha01"
        const val lifecycleCommonVersion = "2.5.0-alpha01"
        const val navigationVersion = Plugin.navigationGradlePluginVersion
        const val dataStoreVersion = "1.0.0"
        const val androidXAnnotation = "1.3.0"
    }

    object Material {
        const val materialVersion = "1.6.0-alpha02"
    }

    object Google {
        const val protoBufJavaLiteVersion = "4.0.0-rc-2"
        const val protoBufProtocVersion = "4.0.0-rc-2"
    }

    object Hilt {
        const val daggerHiltAndroidVersion = Plugin.hiltGradlePluginVersion
        const val daggerHiltAndroidCompilerVersion = Plugin.hiltGradlePluginVersion
        const val androidxHiltNavigation = "1.0.0-alpha03"
    }

    object Network {
        const val okHttpVersion = "5.0.0-alpha.4"
        const val okHttpInterceptorVersion = "5.0.0-alpha.4"
        const val okIOVersion = "3.0.0"
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
