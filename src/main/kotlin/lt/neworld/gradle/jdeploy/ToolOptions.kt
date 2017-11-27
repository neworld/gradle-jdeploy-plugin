package lt.neworld.gradle.jdeploy

import java.io.File

/**
 * @author Andrius Semionovas
 * @since 2017-11-27
 */
data class ToolOptions(
        var version: String = JDeployPlugin.JDEPLOY_VERSION,
        var allowGlobalInstall: Boolean = false,

        val workDir: File,
        val packageFile: File
)