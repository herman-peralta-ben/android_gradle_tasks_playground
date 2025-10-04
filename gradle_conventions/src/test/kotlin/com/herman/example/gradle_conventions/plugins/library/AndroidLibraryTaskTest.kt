package com.herman.example.gradle_conventions.plugins.library

import AndroidLibraryTask
import com.herman.example.gradle_conventions.common.SimpleLogger
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test

class AndroidLibraryTaskTest {
    private val mockLogger: SimpleLogger = mockk {
        every { log(any()) } just Runs
    }

    @Test
    fun `access project metadata from task`() {
        // Arrange: Create in-memory project for module ´android_lib1´ and get a Task instance
        val project = ProjectBuilder.builder()
            .withName("android_lib1")
            .build()
        val task = project.tasks.create("androidLibraryTask", AndroidLibraryTask::class.java)
        task.logger = mockLogger

        // Act
        task.execute()

        // Assert
        val expectedMessage =
            "📌 module: 'android_lib1', relativePath: ':', absolutePath: '${project.projectDir.absolutePath}'"
        verify { mockLogger.log(eq(expectedMessage)) }
        confirmVerified(mockLogger)
    }
}
