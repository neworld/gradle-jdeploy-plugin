package lt.neworld.gradle.jdeploy

import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.kotlin.dsl.invoke
import java.io.File

/**
 * @author Andrius Semionovas
 * @since 2017-11-26
 */
class JDeployExtension(private val project: Project) {
    @get:Input var author: String = ""
    @get:Input var description: String = ""
    @get:Input var license: String = ""
    @get:Input var name: String? = null
    @get:Input var repository: String = ""
    var jar: File? = null

    @get:Input
    val realJar: File
        get() = jar ?: getArchivePath()

    val options: ToolOptions

    fun options(configuration: Closure<ToolOptions>) {
        configuration.delegate = options
        configuration()
    }

    init {
        val workDir = File(project.buildDir, "jdeploy")
        options = ToolOptions(
                workDir = workDir,
                packageFile = File(workDir, "package.json")
        )
    }

    private fun getArchivePath(): File {
        val jarTask = project.tasks.findByName("jar") as org.gradle.api.tasks.bundling.Jar?

        if (jarTask == null) {
            throw IllegalArgumentException("Could not find jar-task. Please make sure you are applying the 'java' plugin or using explicit:\n jdeploy { \n jar = <file> \n }")
        }

        return jarTask.archivePath
    }
}

val Project.jdeployExtension: JDeployExtension
    get() = extensions.findByType(JDeployExtension::class.java) as JDeployExtension
