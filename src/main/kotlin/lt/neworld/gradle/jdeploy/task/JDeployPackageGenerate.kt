package lt.neworld.gradle.jdeploy.task

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import lt.neworld.gradle.jdeploy.JDeployExtension
import lt.neworld.gradle.jdeploy.entity.JDeployEntity
import lt.neworld.gradle.jdeploy.entity.PackageEntity
import lt.neworld.gradle.jdeploy.jdeployExtension
import okio.Okio
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * @author Andrius Semionovas
 * @since 2017-11-27
 */
open class JDeployPackageGenerate : DefaultTask() {
    @get:Nested
    val config: JDeployExtension
        get() = project.jdeployExtension

    private val serializer by lazy {
        Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    @OutputFile
    val output: File = config.options.packageFile

    @TaskAction
    fun generate() {
        val name = config.name
                ?: throw IllegalArgumentException("You must specify the name: \n jdeploy { \n\t name : <app name> \n }")

        val binName = config.binName ?: name

        val toolOptions = config.options

        val packageEntity = PackageEntity(
                bin = mapOf(binName to "jdeploy-bundle/jdeploy.js"),
                author = config.author,
                description = config.description,
                main = "index.js",
                preferGlobal = true,
                repository = config.repository,
                version = project.version as String,
                jdeploy = JDeployEntity(config.realJar.absolutePath),
                license = config.license,
                name = name,
                files = listOf("jdeploy-bundle"),
                dependencies = mapOf("shelljs" to "^0.8.2")
        )

        toolOptions.packageFile.parentFile.mkdirs()

        Okio.buffer(Okio.sink(toolOptions.packageFile)).use {
            serializer.adapter(PackageEntity::class.java).toJson(it, packageEntity)
        }
    }

    companion object {
        const val NAME = "jdeployPackageGenerate"
    }
}
