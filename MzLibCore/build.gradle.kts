plugins {
    java
}

dependencies {
    implementation("io.github.karlatemp:unsafe-accessor:1.6.0")
    implementation("net.bytebuddy:byte-buddy-agent:1.12.22")
    implementation("com.google.code.gson:gson:2.8.9")
//    implementation("com.google.guava:guava:32.0.0-jre")
//    implementation("org.graalvm.polyglot:polyglot:latest.release")
//    implementation("org.graalvm.polyglot:js:latest.release")
}

repositories {
    maven("https://jitpack.io")
}