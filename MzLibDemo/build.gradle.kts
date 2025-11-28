dependencies {
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("net.fabricmc:fabric-loader:0.16.10")
    compileOnly("io.github.karlatemp:unsafe-accessor:1.6.0")
    compileOnly(project(":MzLibCore"))
    compileOnly(project(":MzLibMinecraft"))
    
    // Vert.x dependencies for SimpleDocsServer
    compileOnly("io.vertx:vertx-core:5.0.5")
    compileOnly("io.vertx:vertx-web:5.0.5")

    testImplementation("io.github.karlatemp:unsafe-accessor:1.6.0")
    testImplementation(project(":MzLibCore"))
    testImplementation(project(":MzLibMinecraft"))
    testImplementation(files("../lib"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}