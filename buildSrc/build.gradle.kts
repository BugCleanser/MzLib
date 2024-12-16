plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven { url=uri("https://jitpack.io") }
    gradlePluginPortal()
}

dependencies {
    implementation("com.github.johnrengelman:shadow:7.1.2")
}