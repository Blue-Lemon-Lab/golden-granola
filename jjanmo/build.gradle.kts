plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // 코루틴 설정
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0") // 코루틴 시리얼라이즈 설정
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}