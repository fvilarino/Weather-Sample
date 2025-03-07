import com.android.build.gradle.LibraryExtension
import com.francescsoftware.weathersample.buildconvention.catalog
import com.francescsoftware.weathersample.buildconvention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("kotlin-parcelize")
            pluginManager.apply("com.android.compose.screenshot")
            pluginManager.apply(catalog.findPlugin("org.jetbrains.kotlin.compose.compiler").get().get().pluginId)
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}
