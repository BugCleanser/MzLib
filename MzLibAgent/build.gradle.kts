import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
    id("mz.lib.java-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    implementation("io.github.karlatemp:unsafe-accessor:1.7.0")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("MzLibAgent")
        archiveFileName.set(archiveBaseName.get() + ".jar")
        destinationDirectory.set(File(destinationDirectory.get().asFile.parentFile.parentFile.parentFile, "out"))
        mergeServiceFiles()
        manifest {
            attributes(
                mapOf(
                    "Premain-Class" to "mz.mzlib.util.InstrumentationGetterAgent",
                    "Can-Redefine-Classes" to "true"
                )
            )
        }
    }
}

description = "MzLibAgent"
