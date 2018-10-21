package lt.neworld.gradle.jdeploy.runner

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.exec.ExecRunner
import org.gradle.api.Project
import org.gradle.process.ExecResult
import java.io.File

/**
 * @author Andrius Semionovas
 * @since 2017-11-26
 */
class JDeployRunner(project: Project, val command: String, val env: Env = emptyMap()) : ExecRunner(project) {

    override fun doExecute(): ExecResult {
        environment += env

        val nodeBinDirPath = File(NodeExtension.get(project).nodeModulesDir, ".bin")

        val exec = nodeBinDirPath.absolutePath + "/jdeploy"

        return run(exec, listOf(command))
    }
}

typealias Env = Map<String, String>
