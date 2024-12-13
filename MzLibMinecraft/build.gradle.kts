import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("mz.lib.java-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "mz.mzlib"
version = "10.0.1-beta-dev1"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(project(":MzLibCore"))
    // @Suppress
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")

    compileOnly("com.rylinaux:PlugMan:2.2.9")

    compileOnly("com.mojang:datafixerupper:4.0.26")
    implementation("io.github.karlatemp:unsafe-accessor:1.7.0")
    compileOnly("io.netty:netty-all:4.1.76.Final")
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("MzLibMinecraft")
    archiveFileName.set(archiveBaseName.get() + " v" + archiveVersion.get() + ".jar")
    destinationDirectory.set(File(rootProject.projectDir, "out"))
    mergeServiceFiles()
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}