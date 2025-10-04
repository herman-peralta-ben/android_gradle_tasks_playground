import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)

    testImplementation(kotlin("test"))
    testImplementation(libs.mockK.kotlin)
}

tasks {
    validatePlugins {
        enableStricterValidation.set(true)
        failOnWarning.set(true)
    }
}

gradlePlugin {
    // 💡 The `ids`below are the ones used in ´plugins { alias(id) }´ block in your build.gradle.kts file.
    plugins {
        register("androidApplication") {
            id = "example.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }

        register("androidLibrary") {
            id = "example.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
    }
}
