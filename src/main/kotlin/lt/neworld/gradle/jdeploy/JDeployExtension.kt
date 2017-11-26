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
    var allowGlobalInstall: Boolean = false

    val workDir: File
    val packageFile: File

    init {
        workDir = File(project.buildDir, "jdeploy")
        packageFile = File(workDir, "package.json")
    }
}

val Project.jdeployExtension: JDeployExtension
    get() = extensions.findByType(JDeployExtension::class.java) as JDeployExtension
