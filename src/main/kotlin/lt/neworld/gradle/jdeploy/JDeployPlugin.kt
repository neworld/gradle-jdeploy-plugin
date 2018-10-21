package lt.neworld.gradle.jdeploy

import com.moowork.gradle.node.NodeExtension
import lt.neworld.gradle.jdeploy.task.JDeployPackageGenerate
import lt.neworld.gradle.jdeploy.task.JDeploySetup
import lt.neworld.gradle.jdeploy.task.JDeployTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
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

        project.tasks.create(JDeploySetup.NAME, JDeploySetup::class.java)

        project.tasks.create(JDeployPackageGenerate.NAME, JDeployPackageGenerate::class.java) {
            group = JDeployPlugin.TASK_GROUP
            description = "Prepare package configuration"
        }

        project.tasks.create("jdeployReadme", Copy::class.java) {
            from(project.projectDir)
            into(project.jdeployExtension.options.workDir)
            include("readme.md")

            isCaseSensitive = false
        }

        project.tasks.create("jdeployInstall", JDeployTask::class.java) {
            group = TASK_GROUP
            description = "Install package locally"

            command = "install"
        }

        project.tasks.create("jdeployPublish", JDeployTask::class.java) {
            group = TASK_GROUP
            description = "Publish package to NPM"

            command = "publish"

            npmConfigPrefix = project.jdeployExtension.options.npmFakeConfigPrefix
        }
    }

    companion object {
        const val JDEPLOY_COPY_README_TASK = "jdeployReadme"
        const val TASK_GROUP = "jdeploy"
        const val JDEPLOY_VERSION = "1.0.21"
    }
}
