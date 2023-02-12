plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://lss233.littleservice.cn/repositories/minecraft/") }
    maven { url = uri("https://maven.fastmirror.net/repositories/minecraft/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    maven { url = uri("https://raw.githubusercontent.com/TheBlackEntity/PlugMan/repository/") }
    maven { url = uri("https://repo.maven.apache.org/maven2/") }
}

group = "mz.lib"
version = "10.0.1-pre48-test8"
java.sourceCompatibility = JavaVersion.VERSION_1_8

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
