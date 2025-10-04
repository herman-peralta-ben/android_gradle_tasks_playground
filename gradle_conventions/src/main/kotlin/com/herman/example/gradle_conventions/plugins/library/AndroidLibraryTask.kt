import com.herman.example.gradle_conventions.common.SimpleLogger
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jetbrains.annotations.VisibleForTesting

/**
 * 💡
 * ./gradlew android_lib:androidLibraryTask
 * ./gradlew android_lib2:androidLibraryTask
 */
abstract class AndroidLibraryTask : DefaultTask() {

    @VisibleForTesting()
    var logger = SimpleLogger()

    @TaskAction
    fun execute() {
        logger.log("📌 module: '${project.name}', relativePath: '${project.path}', absolutePath: '${project.projectDir.absolutePath}'")
    }
}
