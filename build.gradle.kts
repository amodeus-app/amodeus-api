val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val skrapeit_version: String by project
val prometeus_version: String by project
val kx_datetime_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

group = "me.evgfilim1.amodeus.api"
version = "0.2.0-alpha.0"
application {
    mainClass.set("me.evgfilim1.amodeus.api.ApplicationKt")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

fun ktorServer(plugin: String) = "io.ktor:ktor-server-$plugin:$ktor_version"
fun ktorClient(plugin: String) = "io.ktor:ktor-client-$plugin:$ktor_version"

dependencies {
    implementation(ktorServer("core"))
    implementation(ktorServer("auth"))
    implementation(ktorServer("resources"))
    implementation(ktorServer("host-common"))
    implementation(ktorServer("status-pages"))
    implementation(ktorServer("content-negotiation"))
    implementation(ktorServer("cors"))
    implementation(ktorServer("forwarded-header"))
    implementation(ktorServer("metrics-micrometer"))
    implementation(ktorServer("netty"))
    implementation(ktorClient("core"))
    implementation(ktorClient("cio"))
    implementation(ktorClient("content-negotiation"))
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.micrometer:micrometer-registry-prometheus:$prometeus_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("it.skrape:skrapeit:$skrapeit_version")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kx_datetime_version")

    testImplementation(ktorServer("tests"))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
