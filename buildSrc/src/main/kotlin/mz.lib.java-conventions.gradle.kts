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
}

group = "mz.mzlib"
version = "10.0.1-beta-dev6"
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
        delete(rootProject.projectDir.resolve("out"))
    }
    named<Jar>("jar") {
        archiveClassifier.set("original")
    }
    named<ShadowJar>("shadowJar") {
        archiveClassifier.set("")
    }
    register<Copy>("copyBinaryResources") {
        from("src/main/resources") {
            include("**/*.lang")
            include("**/*.json")
            include("mappings/yarn/*.tiny")
        }
        into("build/resources/main")
    }
    processResources {
        dependsOn("copyBinaryResources")
        exclude("**/*.lang")
        exclude("**/*.json")
        exclude("mappings/yarn/*.tiny")
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