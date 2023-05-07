import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.util.*

private const val RapidApiKey = "rapid_api_key"
private const val RapiApiKeysFile = "./certs/keys.properties"

open class ConfigKeys {
    val rapidApiKey: String
        get() = key ?: error("Can't locate RapidApi key")

    private var key: String? = null

    internal fun setKey(key: String) {
        this.key = key
    }
}

class KeysLoaderPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create<ConfigKeys>("ConfigKeys")
        val properties = Properties()
        val keysFile = target.rootProject.file(RapiApiKeysFile)
        if (keysFile.exists()) {
            keysFile.inputStream().use { stream ->
                properties.load(stream)
            }
        }
        val key = properties.getProperty(RapidApiKey) ?: System.getenv(RapidApiKey)
        if (key != null) {
            extension.setKey(key)
        }
    }
}
