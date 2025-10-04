import com.herman.example.gradle_conventions.common.SimpleLogger
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.jetbrains.annotations.VisibleForTesting

/**
 * ⚠️ Make sure the plugin and tasks have no package directive
 * ⚠️ Custom [DefaultTask]s should be abstract or open class,
 *     so gradle instantiates them correctly.
 * To use your Task, make sure to:
 * 1. Have a registered plugin, see [AndroidApplicationPlugin].
 * 2. Use the plugin in your module's `build.gradle.kts`.
 * 3. Register the task with
 * ```kotlin
 * tasks.register<HelloWorldTask>("helloWorld") { nameProp.set("World") }
 * ```
 * 4. Run helloWorld on :app is possible since helloWorld is
 * registered in [AndroidApplicationPlugin].
 * ```bash
 * ./gradlew app:helloWorld
 * ./gradlew helloWorld -Pwho=World
 * ```
 * 5. As an example, running helloWorld on :android_lib will break,
 * since is not registered in [AndroidLibraryPlugin] and :android_lib
 * is not applying [AndroidApplicationPlugin].
 * ```bash
 * ./gradlew android_lib:helloWorld
 * ```
 */
abstract class HelloWorldTask : DefaultTask() {

    @get:Input
    abstract val nameProp: Property<String>

    @VisibleForTesting
    var logger = SimpleLogger()

    @TaskAction // This annotation indicates the task entry point method.
    fun execute() {
        logger.log("🎉 Hello, ${nameProp.get()} 🎉")
    }
}
