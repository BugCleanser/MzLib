plugins {
    kotlin("jvm") version "2.0.10"
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson:gson:2.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
//    implementation(project(":MzLibPlugin"))
}