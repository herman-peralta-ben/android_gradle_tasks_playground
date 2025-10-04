import com.herman.example.gradle_conventions.common.SimpleLogger
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.register
import org.jetbrains.annotations.VisibleForTesting

/**
 * Replaces ´libs.plugins.android.library´ and `libs.plugins.kotlin.android` for an
 * Android library module
 */
class AndroidLibraryPlugin : Plugin<Project> {

    @VisibleForTesting
    var logger = SimpleLogger()

    override fun apply(target: Project) {
        applyPlugins(target.pluginManager)
        configureTarget(target)
    }

    @VisibleForTesting
    fun applyPlugins(pluginManager: PluginManager) {
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")
    }

    @VisibleForTesting
    fun configureTarget(target: Project) {
        logger.log("📌 module: '${target.name}', relativePath: '${target.path}', absolutePath: '${target.projectDir.absolutePath}'")

        // 💡 Now "androidLibraryTask" task is included in any module that adds ´libs.plugins.example.android.library´.
        target.tasks.register<AndroidLibraryTask>("androidLibraryTask") {

        }
    }
}
