import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

apply(from = "gradle/utils.gradle.kts")

// === 从 extra 取出函数和路径 ===
val docsDir: File by extra
val deployDir: File by extra
val metaFile: File by extra

val findTypstInPath = extra["findTypstInPath"] as () -> File?
val copyFilesRecursively = extra["copyFilesRecursively"] as (File, File, String?) -> Unit
val generateFileTree = extra["generateFileTree"] as (File, String, Boolean, Appendable) -> Unit

// === 任务：生成 meta.typ ===
tasks.register("generateMeta") {
    group = "docs"
    description = "生成 Typst 元信息文件"

    doLast {
        metaFile.parentFile.mkdirs()
        // TODO("坏文明")
        metaFile.writeText("#let environment = \"production\";\n#let root = \"/MzLib/\";\n#let fileTree = ")
        val builder = StringBuilder()
        generateFileTree(docsDir, "", true, builder)
        metaFile.appendText(builder.toString())
        metaFile.appendText(";")
        println("✅ Generated meta file at: ${metaFile}")
    }
}

// === 任务：编译所有 typst 文件 ===
tasks.register("compileTypst") {
    group = "docs"
    description = "编译所有 Typst 文件为 HTML"

    dependsOn("generateMeta")

    doLast {
        val typst = findTypstInPath() ?: throw GradleException("❌ Typst CLI not found in PATH.")
        println("✅ Using Typst at: ${typst.absolutePath}")

        docsDir.walkTopDown()
            .filter { it.isFile && it.extension == "typ" }
            .forEach { file ->
                val baseName = file.absolutePath.removeSuffix(".typ")
                val htmlFile = File("$baseName.html")
                println("📄 Compiling: ${file.name}")
                val process = ProcessBuilder(
                    typst.absolutePath, "compile",
                    "--features", "html",
                    "--format", "html",
                    "--root", docsDir.absolutePath,
                    file.absolutePath, htmlFile.absolutePath
                ).redirectErrorStream(true) // 合并 stdout + stderr
                    .start()

                val output = process.inputStream.bufferedReader().readText()
                val exit = process.waitFor()

                if (exit != 0) {
                    println("⚠️ Failed to compile ${file.name}")
                    println("---- Typst Output ----")
                    println(output.trim())
                    println("----------------------")
                } else {
                    println("✅ Compiled: ${file.name}")
                }

            }
    }
}

// === 任务：准备部署文件 ===
tasks.register("prepareDeploy") {
    group = "docs"
    description = "准备部署目录"

    dependsOn("compileTypst")

    doLast {
        deployDir.mkdirs()
        copyFilesRecursively(docsDir, deployDir, "typ")
        println("✅ Deployed docs copied to: ${deployDir}")
    }
}

// === 任务：验证部署文件 ===
tasks.register("validateDeploy") {
    group = "docs"
    description = "验证部署目录"

    dependsOn("prepareDeploy")

    doLast {
        println("📦 Deployment directory contents:")
        deployDir.walkTopDown().filter { it.isFile }.sortedBy { it.absolutePath }.forEach { println(it) }
        val htmlCount = deployDir.walkTopDown().count { it.extension == "html" }
        println("\n✅ HTML files found: $htmlCount")
    }
}

// === 一键任务 ===
tasks.register("buildDocs") {
    group = "docs"
    description = "一键生成文档与部署内容"
    dependsOn("validateDeploy")
}

allprojects {
    group = "mz.mzlib"
    version = "10.0.1-beta-dev16"

    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://libraries.minecraft.net/")
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://maven.aliyun.com/repository/gradle-plugin/")
        maven("https://maven.aliyun.com/repository/apache-snapshots/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://raw.githubusercontent.com/TheBlackEntity/PlugMan/repository/")
        //    maven("https://maven.fastmirror.net/repositories/minecraft/")
        //    maven("https://oss.sonatype.org/content/repositories/snapshots")
        //    maven("https://repo.maven.apache.org/maven2/")
    }

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
        withType<JavaCompile> {
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
