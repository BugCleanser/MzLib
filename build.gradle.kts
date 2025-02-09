import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

allprojects {
    group = "mz.mzlib"
    version = "10.0.1-beta-dev12"

    repositories {
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/gradle-plugin/")
        maven("https://maven.aliyun.com/repository/apache-snapshots/")
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("com.github.johnrengelman.shadow")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    val outputDir = File(rootProject.projectDir, "out")

    tasks {
        named<Jar>("jar") {
            archiveClassifier.set("original")
        }
        named<ShadowJar>("shadowJar") {
            archiveClassifier.set("")
        }
        register<Copy>("copyBinaryResources") {
            from("src/main/resources") {
                include("**/*.png")
                include("lang/**/*")
                include("mappings/**/*")
            }
            into("build/resources/main")
        }
        processResources {
            dependsOn("copyBinaryResources")
            exclude("**/*.png")
            exclude("lang/**/*")
            exclude("mappings/**/*")
            expand("version" to project.version)
        }
        withType<JavaCompile>() {
            options.encoding = "UTF-8"
        }
        register<Copy>("moveJarToOutputDir") {
            val shadowJarTask = project.tasks.findByPath("shadowJar") as ShadowJar
            from(shadowJarTask.outputs.files)
            into(outputDir)
        }

        build {
            dependsOn(shadowJar)
            dependsOn("moveJarToOutputDir")
        }
    }
}
