plugins {
    id("java")
    id("io.github.patrick.remapper") version "1.4.0"
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(project(":api"))
    compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT:remapped-mojang")
}

tasks {
    build {
        dependsOn("remap")
    }
    remap {
        version.set("1.20.1")
    }
}