/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
    id("mz.lib.java-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("MzLib")
        archiveName=archiveBaseName.get()+" v"+archiveVersion.get()+".jar";
        destinationDirectory.set(File(destinationDirectory.get().asFile.parentFile.parentFile.parentFile,"target"))
        mergeServiceFiles()
        manifest {
        }
    }
}

dependencies {
    implementation("io.github.karlatemp:unsafe-accessor:1.7.0")
    implementation("net.bytebuddy:byte-buddy-agent:1.12.22")
    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot:1.12.2-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("it.unimi.dsi:fastutil:8.5.11")
    compileOnly("com.rylinaux:PlugMan:2.2.9")
}

description = "MzLibPlugin"
