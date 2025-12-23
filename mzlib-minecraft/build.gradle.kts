dependencies {
    api(project(":mzlib-core"))

    compileOnlyApi("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnlyApi("net.fabricmc:fabric-loader:0.16.10")
    compileOnlyApi("com.rylinaux:PlugMan:2.2.9")
    compileOnlyApi("com.mojang:brigadier:1.3.10")
    compileOnlyApi("io.netty:netty-all:4.1.76.Final")
    compileOnlyApi("net.luckperms:api:5.4")
    compileOnlyApi("it.unimi.dsi:fastutil:7.1.0")
}

ext["publishing"] = true