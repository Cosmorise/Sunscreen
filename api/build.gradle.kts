plugins {
    java
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("net.kyori:adventure-api:4.14.0")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.14.0")
    implementation("io.github.jglrxavpok.hephaistos:common:2.6.0")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.github.hkirk:java-html2image:0.9")
}