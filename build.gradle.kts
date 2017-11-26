val kotlinVersion = "1.1.61"

plugins {
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
}

group = "lt.neworld.gradle"
version = "0.1.0"

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

gradlePlugin {
    (plugins) {
        "jdeploy" {
            id = "lt.neworld.jdeploy"
            implementationClass = "lt.neworld.gradle.jdeploy.JDeployPlugin"
        }
    }
}