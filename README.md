[![GitHub (pre-)release](https://img.shields.io/github/release/neworld/gradle-jdeploy-plugin/all.svg)](https://github.com/neworld/gradle-jdeploy-plugin)
[![Travis-ci](https://travis-ci.org/neworld/gradle-jdeploy-plugin.svg?branch=master)](https://travis-ci.org/neworld/gradle-jdeploy-plugin)


This Gradle plugin is a wrapper for [jDeploy](https://github.com/shannah/jdeploy). 
It helps publish software written in java, kotlin and other JVM languages through [NPM](https://www.npmjs.com/)

### Setup
 
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "lt.neworld.gradle:gradle-jdeploy-plugin:0.5.0"
  }
}

apply plugin: "lt.neworld.jdeploy"
```

or

```groovy
plugins {
  id "lt.neworld.jdeploy" version "0.5.0"
}
```

if you are using [gradle-node-plugin](https://github.com/srs/gradle-node-plugin), you must apply this plugin after it.

### Config

```groovy
jdeploy {
    name = "hello-world"
    binName = "foo" // optional. You could override executable name if you wish. Otherwise uses name
    author = "neworldLT"
    description = "Hello world app for testing purposes"
    license = "MIT"
    repository = "https://github.com/neworld/gradle-jdeploy-plugin"
    
    options {
        toolVersion = "1.0.21" // optional
        allowGlobalInstall = false // optional; explicit enable global install of jdeploy
    }
}
```

Sometimes you want use custom jar packaging task than provided with java plugin.
For example [Shadow](https://github.com/johnrengelman/shadow).
So, you can set jar custom jar path:
```groovy
jdeploy {
    jar = file("$buildDir/libs/$name-$version-all.jar")
}
```

### Compatibility with [gradle-node-plugin](https://github.com/srs/gradle-node-plugin)

Actually, this plugin depends on [gradle-node-plugin](https://github.com/srs/gradle-node-plugin).
I do my best to make sure this plugin plays nicely.
If you are using that node plugin, and you are disabled `download` (which is default behavior), then `jdeploy` needs to be install globally.
However, this could cause undesired outcome.
You should consider use node in the project scope or explicit allow install globally using `allowGlobalInstall` flag.

### Run
```
./gradlew jdeployInstall           #install locally
./gradlew jdeployPublish           #publish to NPM
```

### Contribute

You could easily run all samples to make sure everything is working:
```
cd samples
./gradle smokeTest
```

If you are introducing new functionality, please make sample for it.

### License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
