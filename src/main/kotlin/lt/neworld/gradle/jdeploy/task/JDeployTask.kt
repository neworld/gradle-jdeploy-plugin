package lt.neworld.gradle.jdeploy.task

import lt.neworld.gradle.jdeploy.JDeployPlugin
import lt.neworld.gradle.jdeploy.jdeployExtension
import lt.neworld.gradle.jdeploy.runner.JDeployRunner
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * @author Andrius Semionovas
 * @since 2017-11-26
 */
open class JDeployTask : DefaultTask() {
    init {
        dependsOn(
                JDeploySetup.NAME,
                "jar",
                JDeployPackageGenerate.NAME,
                JDeployPlugin.JDEPLOY_COPY_README_TASK
        )
    }

    @get:Input
    var command: String? = null

    @get:InputFile
    val jar: File
        get() = project.jdeployExtension.realJar

    @TaskAction
    fun exec() {
        val command = command ?: throw IllegalArgumentException("Make sure command is set")

        JDeployRunner(project, command).apply {
            workingDir = project.jdeployExtension.options.workDir
        }.execute()
    }
}
