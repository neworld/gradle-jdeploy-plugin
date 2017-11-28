package lt.neworld.gradle.jdeploy.task

import com.moowork.gradle.node.npm.NpmTask
import groovy.lang.MetaClass
import lt.neworld.gradle.jdeploy.jdeployExtension
import org.gradle.api.tasks.Input

/**
 * @author Andrius Semionovas
 * @since 2017-11-27
 */
class JDeployPrepare2 : NpmTask() {
    @Input
    val version: String = project.jdeployExtension.options.version

    init {
        setArgs(listOf("install", "jdeploy@$version"))
    }

    companion object {
        const val NAME = "jdeployPrepare"
    }
}
