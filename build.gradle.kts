import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

allprojects {
    group = "mz.mzlib"
    version = "10.0.1-beta-dev12"

    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://libraries.minecraft.net/")
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/gradle-plugin/")
        maven("https://maven.aliyun.com/repository/apache-snapshots/")
        maven("https://lss233.littleservice.cn/repositories/minecraft/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://raw.githubusercontent.com/TheBlackEntity/PlugMan/repository/")
    //    maven("https://maven.fastmirror.net/repositories/minecraft/")
    //    maven("https://oss.sonatype.org/content/repositories/snapshots")
    //    maven("https://repo.maven.apache.org/maven2/")
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
        jar {
            archiveClassifier.set("original")
        }
        shadowJar {
            archiveClassifier.set("")
        }
        register<Copy>("copyBinaryResources") {
            from("src/main/resources") {
                include("**/*.js")
                include("**/*.png")
                include("lang/**/*")
                include("mappings/**/*")
            }
            into("build/resources/main")
        }
        processResources {
            dependsOn("copyBinaryResources")
            exclude("**/*.js")
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
