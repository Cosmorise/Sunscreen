plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
}

private val configuration : String = "reobf"

dependencies {
    implementation(project(":api"))
    implementation(project(":spigot-v1_20_1", configuration = configuration))
    implementation("net.kyori:adventure-api:4.14.0")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.14.0")
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
}

tasks {
    build {
        dependsOn( "shadowJar")
    }
}

bukkit {
    name = "Sunscreen"
    main = "me.combimagnetron.sunscreen.SunscreenPlugin"
    apiVersion = "1.20"
}