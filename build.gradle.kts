import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

apply(from = "gradle/utils.gradle.kts")

// === 从 extra 取出函数和路径 ===
val docsDir: File by extra
val deployDir: File by extra

val findTypstInPath = extra["findTypstInPath"] as () -> File?
val copyFilesRecursively = extra["copyFilesRecursively"] as (File, File, String?) -> Unit
val generateFileTree = extra["generateFileTree"] as (File, String, Boolean, Appendable) -> Unit

// === 任务：清除 deploy 目录 ===
tasks.register("cleanDeploy") {
    group = "docs"
    description = "清除 deploy 目录"

    doLast {
        if (deployDir.exists()) {
            deployDir.deleteRecursively()
            println("✅ Cleaned deploy directory: ${deployDir}")
        } else {
            println("ℹ️ Deploy directory does not exist: ${deployDir}")
        }
    }
}

// === 任务：拷贝 docs 到 deploy 目录 ===
tasks.register("copyDocsToDeploy") {
    group = "docs"
    description = "拷贝 docs 目录到 deploy 目录"

    dependsOn("cleanDeploy")

    doLast {
        deployDir.mkdirs()
        copyFilesRecursively(docsDir, deployDir, null)
        println("✅ Docs copied to: ${deployDir}")
    }
}

// === 任务：生成 meta.typ ===
tasks.register("generateMeta") {
    group = "docs"
    description = "生成 Typst 元信息文件"

    dependsOn("copyDocsToDeploy")

    doLast {
        val deployMetaFile = deployDir.resolve("lib/meta.typ")
        deployMetaFile.parentFile.mkdirs()

        deployMetaFile.writeText("#let environment = \"production\";\n#let root = \"/MzLib/\";\n#let fileTree = ")
        val builder = StringBuilder()
        generateFileTree(deployDir, "", true, builder)
        deployMetaFile.appendText(builder.toString())
        deployMetaFile.appendText(";")
        println("✅ Generated meta file at: ${deployMetaFile}")
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

        deployDir.walkTopDown()
            .filter { it.isFile && it.extension == "typ" }
            .forEach { file ->
                val baseName = file.absolutePath.removeSuffix(".typ")
                val htmlFile = File("$baseName.html")
                println("📄 Compiling: ${file.name}")
                val process = ProcessBuilder(
                    typst.absolutePath, "compile",
                    "--features", "html",
                    "--format", "html",
                    "--root", deployDir.absolutePath,
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
        // 删除所有 .typ 文件，因为已经编译成 HTML 了
        deployDir.walkTopDown()
            .filter { it.isFile && it.extension == "typ" }
            .forEach { file ->
                file.delete()
                println("🗑️ Removed .typ file: ${file.relativeTo(deployDir)}")
            }
        
        val typCount = deployDir.walkTopDown().count { it.extension == "typ" }
        if (typCount == 0) {
            println("✅ All .typ files removed successfully")
        } else {
            println("⚠️ Some .typ files may not have been removed")
        }
        
        println("✅ Deploy directory ready at: ${deployDir}")
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

// === 任务：启动 HTTP 预览服务器 ===
tasks.register("serveDocs") {
    group = "docs"
    description = "启动 HTTP 服务器预览 deploy 目录"

    dependsOn("buildDocs", ":MzLibDemo:build")

    doLast {
        val port = 8080

        // 使用 JavaExec 任务运行 SimpleDocsServer
        javaexec {
            mainClass.set("mz.mzlib.demo.SimpleDocsServer")
            classpath = project(":MzLibDemo").sourceSets["main"].runtimeClasspath
            args(deployDir.parent, port.toString())
            standardInput = System.`in`
            standardOutput = System.out
            errorOutput = System.err
        }
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
}

subprojects {
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
