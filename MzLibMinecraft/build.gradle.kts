plugins {
    id("mz.lib.java-conventions")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("com.rylinaux:PlugMan:2.2.9")
    compileOnly("com.mojang:datafixerupper:4.0.26")

    implementation(project(":MzLibCore", configuration = "shadow"))
    implementation("io.github.karlatemp:unsafe-accessor:1.7.0")
    implementation("commons-cli:commons-cli:1.9.0")
    compileOnly("io.netty:netty-all:4.1.76.Final")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "mz.mzlib.cli.CliTool"
    }
}