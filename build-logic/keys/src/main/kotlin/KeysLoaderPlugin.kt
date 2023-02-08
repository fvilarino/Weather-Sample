import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.util.Properties

open class ConfigKeys {
    val rapidApiKey: String
        get() = get("rapid_api_key")

    private var properties: Properties? = null

    fun setProperties(properties: Properties) {
        this.properties = properties
    }

    private fun get(propertyName: String): String {
        var property = properties?.get(propertyName)
        if (property == null) {
            property = System.getenv(propertyName)
        }
        return property?.toString() ?: error("Can't locate property $propertyName")
    }
}

class KeysLoaderPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create<ConfigKeys>("configKeys")
        val properties = Properties()
        val keysFile = target.rootProject.file("./certs/keys.properties")
        if (keysFile.exists()) {
            keysFile.inputStream().use { stream ->
                properties.load(stream)
            }
        }
        extension.setProperties(properties)
    }
}
