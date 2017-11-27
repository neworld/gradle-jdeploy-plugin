package lt.neworld.gradle.jdeploy

import org.gradle.api.Project
import java.io.File

/**
 * @author Andrius Semionovas
 * @since 2017-11-26
 */
class JDeployExtension(project: Project) {
    var author: String = ""
    var description: String = ""
    var license: String = ""
    var name: String? = null
    var repository: String = ""
    var jar: File? = null

    val options: ToolOptions

    fun options(configuration: ToolOptions.() -> Unit) {
        options.configuration()
    }

    init {
        val workDir = File(project.buildDir, "jdeploy")
        options = ToolOptions(
                workDir = workDir,
                packageFile = File(workDir, "package.json")
        )
    }
}

val Project.jdeployExtension: JDeployExtension
    get() = extensions.findByType(JDeployExtension::class.java) as JDeployExtension
