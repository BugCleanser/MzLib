import org.jetbrains.kotlin.gradle.utils.extendsFrom
import java.net.HttpURLConnection
import java.net.URI
import java.util.*

plugins {
    id("java-library")
    kotlin("jvm") version "2.2.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `maven-publish`
    signing
}

val outputDir = File(rootProject.projectDir, "out")

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
        println("✅ Docs copied to: $deployDir")
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

        deployMetaFile.writeText("#let environment = \"production\";\n#let root = \"/mzlib/\";\n#let fileTree = ")
        val builder = StringBuilder()
        generateFileTree(deployDir, "", true, builder)
        deployMetaFile.appendText(builder.toString())
        deployMetaFile.appendText(";")
        println("✅ Generated meta file at: $deployMetaFile")
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

        val files = deployDir.walkTopDown()
            .filter { it.isFile && it.extension == "typ" }
            .toList()

        if (files.isEmpty()) {
            println("ℹ️ No .typ files found in ${deployDir.absolutePath}")
            return@doLast
        }

        files.parallelStream().forEach { file ->
            val baseName = file.absolutePath.removeSuffix(".typ")
            val htmlFile = File("$baseName.html")
            println("📄 Compiling: ${file.name}")

            val process = ProcessBuilder(
                typst.absolutePath, "compile",
                "--features", "html",
                "--format", "html",
                "--root", deployDir.absolutePath,
                file.absolutePath, htmlFile.absolutePath
            ).redirectErrorStream(true)
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

    dependsOn("buildDocs", ":mzlib-demo:build")

    doLast {
        val port = 8080

        // 使用 JavaExec 任务运行 SimpleDocsServer
        javaexec {
            mainClass.set("mz.mzlib.demo.SimpleDocsServer")
            classpath = project(":mzlib-demo").sourceSets["main"].runtimeClasspath
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

val isSnapshot = !(System.getenv("BUILD_TYPE")?.equals("release", ignoreCase = true) ?: false)

allprojects {
    group = "org.mzverse"
    val baseVersion = "10.0.1-beta.18"
    version = if (isSnapshot) {
        "$baseVersion-SNAPSHOT"
    } else {
        baseVersion
    }

    repositories {
        mavenCentral()
        maven {
            name = "Central Portal Snapshots"
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
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
        plugin("java-library")
        plugin("kotlin")
        plugin("com.github.johnrengelman.shadow")
        plugin("maven-publish")
        plugin("signing")
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
            apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_5)
            languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_5)
        }
    }
    dependencies {
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:latest.release")
        compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:latest.release")
    }
}

subprojects {
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        withSourcesJar()
        withJavadocJar()
    }

    configurations {
        testCompileOnly.extendsFrom(compileOnly)
    }

    components {
        withType<AdhocComponentWithVariants> {
            withVariantsFromConfiguration(configurations.shadowRuntimeElements.get()) {
                skip() // 避免shadowJar被publish
            }
        }
    }

    dependencies {
        testImplementation(kotlin("test"))
        testImplementation("org.junit.jupiter:junit-jupiter:5.13.2")
    }
    tasks.test {
        useJUnitPlatform()
    }
    tasks {
        javadoc {
            isFailOnError = false;
            (options as StandardJavadocDocletOptions).addBooleanOption("Xdoclint:-missing", true)
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
        shadowJar {
            destinationDirectory = outputDir
            mergeServiceFiles()
        }
        withType<Javadoc> {
            options {
                jFlags = listOf(
                    "-Dfile.encoding=UTF-8",
                    "-Dsun.jnu.encoding=UTF-8",
                    "-Dnative.encoding=UTF-8",
                    "-Dsun.stdout.encoding=UTF-8",
                    "-Dsun.stderr.encoding=UTF-8"
                )
                encoding = "UTF-8"
                this as StandardJavadocDocletOptions
                charSet = "UTF-8"
                docEncoding = "UTF-8"
            }
        }
        build {
            dependsOn(shadowJar)
            dependsOn(publishToMavenLocal)
        }
    }

    afterEvaluate {
        if(extra.has("publishing")) {
            publishing {
                publications {
                    create<MavenPublication>("maven") {
                        groupId = project.group.toString()
                        artifactId = project.name
                        version = project.version.toString()

                        from(components["java"])

                        pom {
                            name = project.name
                            description = project.description
                            url = "https://github.com/mzverse/mzlib"
                            developers {
                                developer {
                                    id = "mzmzpwq"
                                    name = "mz"
                                    email = "2323346933@qq.com"
                                    url = "https://github.com/mzmzpwq"
                                }
                            }
                            licenses {
                                license {
                                    name.set("Mozilla Public License Version 2.0")
                                    url.set("https://www.mozilla.org/en-US/MPL/2.0/")
                                }
                            }
                            scm {
                                connection.set("scm:git:git://github.com/mzverse/mzlib.git")
                                developerConnection.set("scm:git:ssh://github.com/mzverse/mzlib.git")
                                url.set("https://github.com/mzverse/mzlib")
                                tag.set("v"+project.version)
                            }
                            issueManagement {
                                system.set("GitHub Issues")
                                url.set("https://github.com/mzverse/mzlib/issues")
                            }
                        }
                    }
                }
            }
            if(System.getenv("CI") != null) {
                publishing {
                    repositories {
                        maven {
                            name = "MavenCentral"
                            url = if (isSnapshot)
                                uri("https://central.sonatype.com/repository/maven-snapshots/")
                            else
                                uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
                            credentials {
                                username = System.getenv("OSSRH_USERNAME")
                                password = System.getenv("OSSRH_PASSWORD")
                            }
                        }
                        maven {
                            name = "GitHubPackages"
                            url = uri("https://maven.pkg.github.com/mzverse/mzlib")
                            credentials {
                                username = System.getenv("GITHUB_ACTOR")
                                password = System.getenv("GITHUB_TOKEN")
                            }
                        }
                    }
                }
                if(!isSnapshot) {
                    tasks["publishMavenPublicationToMavenCentralRepository"].doLast {
                        with(
                            URI("https://ossrh-staging-api.central.sonatype.com/manual/upload/defaultRepository/${project.group}?publishing_type=automatic").toURL()
                                .openConnection() as HttpURLConnection
                        ) {
                            requestMethod = "POST"
                            val token = Base64.getEncoder()
                                .encodeToString("${System.getenv("OSSRH_USERNAME")}:${System.getenv("OSSRH_PASSWORD")}".toByteArray())
                                .trim()
                            setRequestProperty("Authorization", "Bearer $token")
                            getInputStream()
                            println("✅ Published to MavenCentral: $responseCode")
                        }
                    }
                }
                signing {
                    useInMemoryPgpKeys(System.getenv("PGP_KEY"), System.getenv("PGP_PASSWORD"))
                    sign(publishing.publications["maven"])
                }
            }
        }
    }
}
