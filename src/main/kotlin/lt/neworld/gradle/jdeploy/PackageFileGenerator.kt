package lt.neworld.gradle.jdeploy

import com.moowork.gradle.node.NodeExtension
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import lt.neworld.gradle.jdeploy.entity.JDeployEntity
import lt.neworld.gradle.jdeploy.entity.PackageEntity
import okio.Okio
import org.gradle.api.Project
import java.io.File

/**
 * @author Andrius Semionovas
 * @since 2017-11-26
 */
class PackageFileGenerator(private val project: Project, private val config: JDeployExtension) {
    private val serializer by lazy {
        Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    fun getArchivePath(): File {
        if (config.jar != null) return config.jar!!

        val jarTask = project.tasks.findByName("jar") as org.gradle.api.tasks.bundling.Jar?

        if (jarTask == null) {
            throw IllegalArgumentException("Could not find jar-task. Please make sure you are applying the 'java' plugin or using explicit:\n jdeploy { \n jar = <file> \n }")
        }

        return jarTask.archivePath
    }

    fun generate() {
        val name = config.name ?: throw IllegalArgumentException("You must specify the name: \n jdeploy { \n\t name : <app name> \n }")

        if (NodeExtension.get(project).download && config.allowGlobalInstall) {
            throw IllegalArgumentException("""You are using global npm.
                You have to explicit let plugin install jdeploy globally:

                jdeploy {
                    allowGlobalInstall = false
                }

                or enable local copy of npm:

                node {
                    download = true
                }
            """);
        }

        val packageEntity = PackageEntity(
                bin = mapOf(name to "jdeploy-bundle/jdeploy.js"),
                author = config.author,
                description = config.description,
                main = "index.js",
                preferGlobal = true,
                repository = config.repository,
                version = project.version as String,
                jdeploy = JDeployEntity(getArchivePath().absolutePath),
                license = config.license,
                name = name,
                files = listOf("jdeploy-bundle")
        )

        config.packageFile.parentFile.mkdirs()

        Okio.buffer(Okio.sink(config.packageFile)).use {
            serializer.adapter(PackageEntity::class.java).toJson(it, packageEntity)
        }
    }
}