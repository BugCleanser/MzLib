import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("mz.lib.java-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("MzLib")
        archiveFileName.set(archiveBaseName.get() + " v" + archiveVersion.get() + ".jar")
        destinationDirectory.set(File(destinationDirectory.get().asFile.parentFile.parentFile.parentFile, "out"))
        mergeServiceFiles()
        manifest {
            attributes["Main-Class"] = "mz.mzlib.plugin.PluginManager"
        }
    }
    build {
        dependsOn(shadowJar)
    }
}

dependencies {
    implementation("io.github.karlatemp:unsafe-accessor:1.7.0")
    implementation("net.bytebuddy:byte-buddy-agent:1.12.22")

//    implementation("org.graalvm.polyglot:polyglot:latest.release")
//    implementation("org.graalvm.polyglot:js:latest.release")

    api(project(":Mappings"))

//    compileOnly("net.md-5:bungeecord-api:1.12-SNAPSHOT")
    @Suppress
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("it.unimi.dsi:fastutil:8.5.11")

    compileOnly("io.netty:netty-all:4.1.76.Final")

    compileOnly("com.rylinaux:PlugMan:2.2.9")

    compileOnly("com.mojang:datafixerupper:1.0.20")
}

description = "MzLibPlugin"
