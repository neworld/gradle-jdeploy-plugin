import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.10.0"
}

group = "lt.neworld.gradle"
version = "0.5.1"

repositories {
    maven {
        setUrl("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    compile("com.moowork.gradle:gradle-node-plugin:1.2.0")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlinVersion")
    compile("com.squareup.moshi:moshi:1.7.0")
    compile("com.squareup.moshi:moshi-kotlin:1.7.0")
    testCompile("junit:junit:4.12")
}

val sourcesJar by tasks.creating(Jar::class) {
    setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE)
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

val archives = configurations.getByName("archives")

artifacts.add(archives.name, sourcesJar) {
    builtBy(sourcesJar)
}

gradlePlugin {
    plugins {
        register("jdeploy") {
            id = "lt.neworld.jdeploy"
            implementationClass = "lt.neworld.gradle.jdeploy.JDeployPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/neworld/gradle-jdeploy-plugin"
    vcsUrl = "https://github.com/neworld/gradle-jdeploy-plugin"
    description = "Plugin for publishing JVM software through NPM."
    tags = listOf("jdeploy", "publishing", "npm")

    (plugins) {
        "jdeploy" {
            id = "lt.neworld.jdeploy"
            displayName = "jDeploy plugin"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}
