package com.herman.example.gradle_conventions.tasks

import HelloWorldTask
import com.herman.example.gradle_conventions.common.SimpleLogger
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test

// ./gradlew :gradle_conventions:test --tests "com.herman.example.gradle_conventions.tasks.HelloWorldTaskTest"
class HelloWorldTaskTest {

    private val mockLogger: SimpleLogger = mockk {
        every { log(any()) } just Runs
    }

    @Test
    fun `helloWorld logs the correct message`() {
        // Arrange
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("helloWorld", HelloWorldTask::class.java)
        task.nameProp.set("🌎")
        task.logger = mockLogger

        // Act
        task.execute()

        // Assert
        verify { mockLogger.log(eq("🎉 Hello, 🌎 🎉")) }
        confirmVerified(mockLogger)
    }
}
