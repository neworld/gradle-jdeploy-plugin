package lt.neworld.gradle.jdeploy.entity

/**
 * @author Andrius Semionovas
 * @since 2017-11-26
 */
data class PackageEntity(
        val bin: Map<String, String>,
        val preferGlobal: Boolean,
        val version: String,
        val jdeploy: JDeployEntity,
        val license: String,
        val name: String,
        val files: List<String>,
        val author: String,
        val description: String,
        val repository: String,
        val main: String,
        val dependencies: Map<String, String>
)
