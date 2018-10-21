package lt.neworld.gradle.jdeploy.task

import lt.neworld.gradle.jdeploy.JDeployPlugin
import lt.neworld.gradle.jdeploy.jdeployExtension
import lt.neworld.gradle.jdeploy.runner.JDeployRunner
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
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

    @get:[InputDirectory Optional]
    var npmConfigPrefix: File? = null

    @TaskAction
    fun exec() {
        val command = command ?: throw IllegalArgumentException("Make sure command is set")

        val env = if (npmConfigPrefix != null) {
            mapOf("NPM_CONFIG_PREFIX" to npmConfigPrefix!!.path)
        } else {
            emptyMap()
        }

        JDeployRunner(project, command, env).apply {
            workingDir = project.jdeployExtension.options.workDir
        }.execute()
    }
}
