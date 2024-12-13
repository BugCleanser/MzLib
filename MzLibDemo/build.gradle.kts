plugins {
    java
    id("mz.lib.java-conventions")
}

group = "mz.mzlib.demo"
version = "0.1"
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
    implementation(fileTree("./lib"))
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")

    implementation(project(":MzLibCore"))
    implementation(project(":MzLibMinecraft"))
}

val outputDir = File(rootProject.projectDir, "out")

tasks.register<Copy>("moveJarToOutputDir") {
    // 源文件为构建生成的 JAR 文件
    from(tasks.jar.get().outputs.files)
    // 目标目录
    into(outputDir)
}

tasks {
    build {
        dependsOn("moveJarToOutputDir")
    }
}
