package com.herman.example.gradle_conventions.plugins.library

import AndroidLibraryPlugin
import AndroidLibraryTask
import com.herman.example.gradle_conventions.common.SimpleLogger
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.get
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AndroidLibraryPluginTest {

    @Test
    fun `applyPlugins applies library and kotlin plugins`() {
        // Arrange
        val mockPluginManager = mockk<PluginManager>(relaxed = true)
        val plugin = AndroidLibraryPlugin()

        // Act
        plugin.applyPlugins(mockPluginManager)

        // Assert
        verify { mockPluginManager.apply("com.android.library") }
        verify { mockPluginManager.apply("org.jetbrains.kotlin.android") }
    }

    @Test
    fun `configureTarget registers androidLibrary task`() {
        // Arrange: Create in-memory project and get a Plugin instance
        val project = ProjectBuilder.builder().build()
        val plugin = AndroidLibraryPlugin()

        // Act
        plugin.configureTarget(project)

        // Assert
        val androidLibraryTask = project.tasks["androidLibraryTask"]
        assertNotNull(androidLibraryTask)
        assertTrue(androidLibraryTask is AndroidLibraryTask)
    }

    @Test
    fun `access project metadata from plugin`() {
        // Arrange: Create in-memory project for module ´android_lib1´ and get a Task instance
        val messageSlot = slot<String>()
        val mockLogger: SimpleLogger = mockk {
            every { log(capture(messageSlot)) } just Runs
        }
        val project = ProjectBuilder.builder()
            .withName("android_lib1")
            .build()
        val plugin = AndroidLibraryPlugin()
        plugin.logger = mockLogger

        // Act
        plugin.configureTarget(project)
        println(messageSlot)

        // Assert
        val expectedMessage =
            "📌 module: 'android_lib1', relativePath: ':', absolutePath: '${project.projectDir.absolutePath}'"
        verify { mockLogger.log(eq(expectedMessage)) }
        confirmVerified(mockLogger)
    }
}
