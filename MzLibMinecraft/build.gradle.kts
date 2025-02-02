plugins {
    id("mz.lib.java-conventions")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("com.rylinaux:PlugMan:2.2.9")
    compileOnly("com.mojang:datafixerupper:4.0.26")
    compileOnly("com.mojang:brigadier:1.3.10")

    api(project(":MzLibCore"))
    compileOnly("io.netty:netty-all:4.1.76.Final")
}