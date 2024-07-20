plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val kotlinVersion = "1.8.1"

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinVersion}")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${kotlinVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:${kotlinVersion}")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("ch.qos.logback:logback-classic:1.4.12")

    implementation(platform("io.arrow-kt:arrow-stack:1.2.4"))
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx-coroutines")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${kotlinVersion}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.4.0")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

tasks {
    test {
        useJUnitPlatform()
    }
}
