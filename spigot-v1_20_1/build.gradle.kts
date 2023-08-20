plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.5"
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
}

dependencies {
    implementation(project(":api"))
    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.14.0")
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

}
