import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.kotlin)
}


group = "io.github.solid-resourcepack"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    testImplementation(libs.kotlinTest)
    implementation(libs.kotlinJvm)
    implementation(libs.solid)
    implementation(libs.paper)
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.named("shadowJar", ShadowJar::class) {
    mergeServiceFiles()
    archiveFileName.set("${project.name}.jar")
}

tasks.test {
    useJUnitPlatform()
}


