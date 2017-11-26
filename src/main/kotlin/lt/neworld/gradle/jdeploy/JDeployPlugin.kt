package lt.neworld.gradle.jdeploy

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.npm.NpmTask
import lt.neworld.gradle.jdeploy.task.JDeployTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * @author Andrius Semionovas
 * @since 2017-11-23
 */
class JDeployPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val hasNpmPlugin = project.plugins.findPlugin("com.moowork.node")
        if (hasNpmPlugin == null) {
            project.plugins.apply("com.moowork.node")
            with(NodeExtension.get(project)) {
                download = true
                nodeModulesDir = File(project.buildDir, "node_modules")
            }
        }

        val jdeployExtension = JDeployExtension(project)
        project.extensions.add("jdeploy", jdeployExtension)

        val prepareTask = project.tasks.create("jdeployPrepare", NpmTask::class.java) {
            group = TASK_GROUP
            description = "Prepare jdeploy and configuration"

            setArgs(listOf("install", "jdeploy"))
            doLast {
                PackageFileGenerator(project, jdeployExtension).generate()
            }
        }

        project.tasks.create("jdeployInstall", JDeployTask::class.java) {
            group = TASK_GROUP
            description = "Install package locally"

            dependsOn(prepareTask)
            dependsOn("jar")
            command = "install"
        }

        project.tasks.create("jdeployPublish", JDeployTask::class.java) {
            group = TASK_GROUP
            description = "Publish package to NPM"

            dependsOn(prepareTask)
            dependsOn("jar")
            command = "publish"
        }
    }

    companion object {
        private const val TASK_GROUP = "jdeploy"
    }
}
