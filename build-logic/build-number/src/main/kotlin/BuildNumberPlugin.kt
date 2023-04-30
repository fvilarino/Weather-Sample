import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.util.Properties

private const val PropertyName = "buildNumber"
private const val PropertiesFile = "build_number.properties"

/** Build number property holder */
open class BuildNumber {
    /** The version code to use for the build */
    val versionCode: Long
        get() {
            val property = (properties?.get(PropertyName) ?: System.getenv(PropertyName))?.toString()
            return property?.toLongOrNull() ?: error("Could not locate the build number")
        }

    private var properties: Properties? = null

    /**
     * Sets the [Properties] containing the build number
     *
     * @param properties the [Properties] with the build number
     */
    fun setProperties(properties: Properties) {
        this.properties = properties
    }
}

/** The build number plug-in */
class BuildNumberPlugin : Plugin<Project> {

    /** @{inheritDoc} */
    override fun apply(target: Project) {
        val extension = target.extensions.create<BuildNumber>("buildNumber")
        val properties = Properties()
        val buildNumberFile = target.rootProject.file(PropertiesFile)
        if (buildNumberFile.exists()) {
            buildNumberFile.inputStream().use { stream ->
                properties.load(stream)
            }
        }
        extension.setProperties(properties)
    }
}
