package com.francescsoftware.weathersample.buildconvention

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.maybeCreate

private enum class TestSystemImage(val imageName: String) {
    AospAtd("aosp-atd"),
    Aosp("aosp"),
    Google("google");

    val sanitizedName: String
        get() = imageName.replace("-", "")
}

private data class TestDevice(
    val name: String,
    val apiLevel: Int,
    val systemImage: TestSystemImage,
) {
    val identifier: String
        get() = name.lowercase().replace(" ", "") +
            apiLevel.toString() +
            systemImage.sanitizedName
}

private val Pixel6 = TestDevice(
    name = "Pixel 6",
    apiLevel = 31,
    systemImage = TestSystemImage.Aosp,
)

private val Pixel4 = TestDevice(
    name = "Pixel 4",
    apiLevel = 30,
    systemImage = TestSystemImage.AospAtd,
)

private val PixelC = TestDevice(
    name = "Pixel C",
    apiLevel = 30,
    systemImage = TestSystemImage.AospAtd,
)

private val AllTestDevices = listOf(Pixel6, Pixel4, PixelC)
private val LocalTestDevices = AllTestDevices
private val CiTestDevices = AllTestDevices.filter { testDevice ->
    testDevice.systemImage == TestSystemImage.AospAtd
}

internal fun configureAndroidTest(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        testOptions {
            unitTests.all { test ->
                test.useJUnitPlatform {
                    includeEngines("junit-jupiter")
                }
                test.testLogging {
                    events("passed", "skipped", "failed")
                    showStandardStreams = true
                    showStackTraces = true
                    showCauses = true
                    exceptionFormat = TestExceptionFormat.FULL
                }
            }

            managedDevices {
                devices {
                    AllTestDevices.forEach { testDevice ->
                        maybeCreate<ManagedVirtualDevice>(testDevice.identifier).apply {
                            device = testDevice.name
                            apiLevel = testDevice.apiLevel
                            systemImageSource = testDevice.systemImage.imageName
                        }
                    }
                }
                groups {
                    maybeCreate("local").apply {
                        LocalTestDevices.forEach { testDevice ->
                            targetDevices.add(devices[testDevice.identifier])
                        }
                    }
                    maybeCreate("ci").apply {
                        CiTestDevices.forEach { testDevice ->
                            targetDevices.add(devices[testDevice.identifier])
                        }
                    }
                }
            }
        }
    }
}

internal fun LibraryAndroidComponentsExtension.disableUnnecessaryAndroidTests(
    project: Project,
) = beforeVariants { libraryVariantBuilder ->
    libraryVariantBuilder.enableAndroidTest = libraryVariantBuilder.enableAndroidTest &&
        project.projectDir.resolve("src/androidTest").exists()
}
