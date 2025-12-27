dependencies {
    api(project(":mzlib-core"))

    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("net.fabricmc:fabric-loader:0.16.10")
    compileOnly("com.rylinaux:PlugMan:2.2.9")
    compileOnly("com.mojang:brigadier:1.3.10")
    compileOnly("io.netty:netty-all:4.1.76.Final")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("it.unimi.dsi:fastutil:7.1.0")
    compileOnly("com.mojang:datafixerupper:4.0.26")
}

ext["publishing"] = true