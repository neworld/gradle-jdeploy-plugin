val kotlinVersion = "1.1.61"

plugins {
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.9.9"
}

group = "lt.neworld.gradle"
version = "0.2.0"

repositories {
    maven {
        setUrl("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    compile("com.moowork.gradle:gradle-node-plugin:1.2.0")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlinVersion")
    compile("com.squareup.moshi:moshi:1.5.0")
    compile("com.squareup.moshi:moshi-kotlin:1.5.0")
    testCompile("junit:junit:4.12")
}

val sourcesJar by tasks.creating(Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    classifier = "sources"
    from(java.sourceSets["main"].allSource)
}

val archives = configurations.getByName("archives")

artifacts.add(archives.name, sourcesJar) {
    builtBy(sourcesJar)
}

gradlePlugin {
    (plugins) {
        "jdeploy" {
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
