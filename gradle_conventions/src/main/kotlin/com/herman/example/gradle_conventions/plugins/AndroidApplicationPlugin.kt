import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.register
import org.jetbrains.annotations.VisibleForTesting

/**
 * ⚠️ Register the plugin in the `gradlePlugin` block on `gradle_conventions/build.gradle.kts`.
 * Usage: `plugins { alias(libs.plugins.example.android.application) }`
 * ⚠️ Make sure the plugin and tasks have no package directive
 */
class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        applyPlugins(target.pluginManager)
        configureTarget(target)
    }

    @VisibleForTesting
    fun applyPlugins(pluginManager: PluginManager) {
        pluginManager.apply("com.android.application")
        pluginManager.apply("org.jetbrains.kotlin.android")
    }

    @VisibleForTesting
    fun configureTarget(target: Project) {
        target.extensions.extraProperties.set("myProperty", "Hello World")

        // 💡 Now "helloWorld" task is included in any module that adds ´libs.plugins.example.android.application´.
        target.tasks.register<HelloWorldTask>("helloWorld") {
            if (project.hasProperty("who")) {
                // `./gradlew helloWorld -Pwho=Herman`
                nameProp.set(project.property("who").toString())
            } else {
                // `./gradlew helloWorld`
                nameProp.set("World")
            }
        }
    }
}
