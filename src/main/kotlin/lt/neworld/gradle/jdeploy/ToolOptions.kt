package lt.neworld.gradle.jdeploy

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import java.io.File

/**
 * @author Andrius Semionovas
 * @since 2017-11-27
 */
data class ToolOptions(
        @get:Input
        var toolVersion: String = JDeployPlugin.JDEPLOY_VERSION,
        @get:Input
        var allowGlobalInstall: Boolean = false,

        @get:OutputDirectory
        val workDir: File,
        @get:OutputFile
        val packageFile: File
)
