import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenLocal()
    maven("https://libraries.minecraft.net/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://lss233.littleservice.cn/repositories/minecraft/")
    maven("https://maven.fastmirror.net/repositories/minecraft/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://raw.githubusercontent.com/TheBlackEntity/PlugMan/repository/")
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://maven.fabricmc.net/")
}

group = "mz.mzlib"
version = "10.0.1-beta-dev10"
java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

val outputDir = File(rootProject.projectDir, "out")

tasks {
    clean {
        delete(outputDir)
    }
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
        from(tasks.named<ShadowJar>("shadowJar").get().outputs.files)
        into(outputDir)
    }
    build {
        dependsOn(shadowJar)
        dependsOn("moveJarToOutputDir")
    }
}