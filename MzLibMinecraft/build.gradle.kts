plugins {
    id("mz.lib.java-conventions")
}

dependencies {
    implementation(project(":MzLibCore", configuration = "shadow"))
    // @Suppress
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")

    compileOnly("com.rylinaux:PlugMan:2.2.9")

    compileOnly("com.mojang:datafixerupper:4.0.26")
    implementation("io.github.karlatemp:unsafe-accessor:1.7.0")
    compileOnly("io.netty:netty-all:4.1.76.Final")
}
