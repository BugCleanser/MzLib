plugins {
    id("mz.lib.java-conventions")
}

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public/")
    maven("https://maven.aliyun.com/repository/gradle-plugin/")
    maven("https://maven.aliyun.com/repository/apache-snapshots/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://libraries.minecraft.net/")
    mavenCentral()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("net.fabricmc:fabric-loader:0.16.10")
    compileOnly(project(":MzLibCore"))
    compileOnly(project(":MzLibMinecraft"))

    testImplementation(fileTree("../lib"))
    testImplementation(project(":MzLibMinecraft"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.test {
    useJUnitPlatform()
}