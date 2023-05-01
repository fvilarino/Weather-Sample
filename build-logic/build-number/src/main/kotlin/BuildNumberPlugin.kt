import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.util.Properties

private const val PropertyName = "buildNumber"
private const val PropertiesFile = "build_number.properties"

/** Build number extension */
open class BuildNumber {
    /** The version code to use for the build */
    val versionCode: Long
        get() = buildNumber ?: error("Could not locate the build number")

    private var buildNumber: Long? = null

    /**
     * Sets the build number to use
     *
     * @param buildNumber the number to use for the build
     */
    internal fun setBuildNumber(buildNumber: Long) {
        this.buildNumber = buildNumber
    }
}

/** The build number plug-in */
class BuildNumberPlugin : Plugin<Project> {

    /** @{inheritDoc} */
    override fun apply(project: Project) {
        val extension = project.extensions.create<BuildNumber>("BuildNumber")
        val buildNumberFile = project.rootProject.file(PropertiesFile)
        val buildNumber = when {
            project.hasProperty(PropertyName) -> project.properties[PropertyName]?.toString()?.toLongOrNull()
            buildNumberFile.exists() -> {
                val properties = Properties()
                buildNumberFile.inputStream().use { stream ->
                    properties.load(stream)
                }
                properties[PropertyName]?.toString()?.toLongOrNull()
            }
            else -> System.getenv(PropertyName)?.toString()?.toLongOrNull()
        }
        if (buildNumber != null) {
            extension.setBuildNumber(buildNumber)
        }
    }
}
