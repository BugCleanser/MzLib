dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    api("io.github.karlatemp:unsafe-accessor:1.7.0")
    api("org.mozilla:rhino:1.7.15.1")
    api("com.google.code.gson:gson:2.8.9")
    api("net.bytebuddy:byte-buddy-agent:1.12.22")
//    api("com.google.guava:guava:32.0.0-jre")
//    api("org.graalvm.polyglot:polyglot:latest.release")
//    api("org.graalvm.polyglot:js:latest.release")
    compileOnlyApi("jakarta.annotation:jakarta.annotation-api:latest.release")

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

ext["publishing"] = true