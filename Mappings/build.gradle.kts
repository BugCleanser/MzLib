plugins {
    id("java")
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    @Suppress
    implementation("com.google.code.gson:gson:2.8.0")
}