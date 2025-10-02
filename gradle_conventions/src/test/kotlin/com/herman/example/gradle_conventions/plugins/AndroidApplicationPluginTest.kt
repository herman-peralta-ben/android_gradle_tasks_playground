package com.herman.example.gradle_conventions.plugins

import AndroidApplicationPlugin
import io.mockk.mockk
import io.mockk.verify
import org.gradle.api.plugins.PluginManager
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertEquals

// ./gradlew :gradle_conventions:test --tests "com.herman.example.gradle_conventions.plugins.AndroidApplicationPluginTest"
class AndroidApplicationPluginTest {

    @Test
    fun `applyPlugins applies android and kotlin plugins`() {
        // Arrange
        val mockPluginManager = mockk<PluginManager>(relaxed = true)
        val plugin = AndroidApplicationPlugin()

        // Act
        plugin.applyPlugins(mockPluginManager)

        // Assert
        verify { mockPluginManager.apply("com.android.application") }
        verify { mockPluginManager.apply("org.jetbrains.kotlin.android") }
    }

    @Test
    fun `configureTarget sets myProperty correctly`() {
        // Arrange: Create in-memory project and get a Plugin instance
        val project = ProjectBuilder.builder().build()
        val plugin = AndroidApplicationPlugin()

        // Act
        plugin.configureTarget(project)

        // Assert
        val prop = project.extensions.extraProperties["myProperty"] as String
        assertEquals("Hello World", prop)
    }
}
