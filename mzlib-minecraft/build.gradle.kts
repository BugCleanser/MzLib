dependencies {
    api(project(":mzlib-core"))
    api("io.github.karlatemp:unsafe-accessor:1.7.0")
    api("org.mozilla:rhino:1.7.15.1")
    api("com.google.code.gson:gson:2.8.9")

    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("net.fabricmc:fabric-loader:0.16.10")
    compileOnly("com.rylinaux:PlugMan:2.2.9")
    compileOnly("com.mojang:datafixerupper:4.0.26")
    compileOnly("com.mojang:brigadier:1.3.10")
    compileOnly("io.netty:netty-all:4.1.76.Final")
    compileOnly("net.luckperms:api:5.4")
}

ext["publishing"] = true