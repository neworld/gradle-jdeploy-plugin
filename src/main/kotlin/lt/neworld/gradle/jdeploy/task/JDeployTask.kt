package lt.neworld.gradle.jdeploy.task

import lt.neworld.gradle.jdeploy.jdeployExtension
import lt.neworld.gradle.jdeploy.runner.JDeployRunner
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * @author Andrius Semionovas
 * @since 2017-11-26
 */
open class JDeployTask : DefaultTask() {
    @Input
    var command: String? = null

    @TaskAction
    fun exec() {
        val command = command ?: throw IllegalArgumentException("Make sure command is set")

        JDeployRunner(project, command).apply {
            workingDir = project.jdeployExtension.workDir
        }.execute()
    }
}