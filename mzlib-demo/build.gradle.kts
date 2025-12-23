dependencies {
    compileOnlyApi("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnlyApi("net.fabricmc:fabric-loader:0.16.10")
    compileOnlyApi("io.github.karlatemp:unsafe-accessor:1.6.0")
    compileOnlyApi(project(":mzlib-minecraft"))
    
    // Vert.x dependencies for SimpleDocsServer
    implementation("io.vertx:vertx-core:5.0.5")
    implementation("io.vertx:vertx-web:5.0.5")

    testImplementation("io.github.karlatemp:unsafe-accessor:1.6.0")
    testImplementation(project(":mzlib-core"))
    testImplementation(project(":mzlib-minecraft"))
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