dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    implementation("io.github.karlatemp:unsafe-accessor:1.6.0")
    implementation("org.mozilla:rhino:1.7.15")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("net.bytebuddy:byte-buddy-agent:1.12.22")
//    implementation("com.google.guava:guava:32.0.0-jre")
//    implementation("org.graalvm.polyglot:polyglot:latest.release")
//    implementation("org.graalvm.polyglot:js:latest.release")

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
