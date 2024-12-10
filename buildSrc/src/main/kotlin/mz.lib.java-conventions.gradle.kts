plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://lss233.littleservice.cn/repositories/minecraft/")
    maven("https://maven.fastmirror.net/repositories/minecraft/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://raw.githubusercontent.com/TheBlackEntity/PlugMan/repository/")
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://libraries.minecraft.net/")
}

group = "mz.mzlib"
version = "10.0.1-beta-dev1"
java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.processResources{
    expand("version" to project.version)
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}