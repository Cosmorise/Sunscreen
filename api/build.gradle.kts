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
    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.14.0")
    implementation("io.github.jglrxavpok.hephaistos:common:2.6.0")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.typesafe:config:1.4.2")
    implementation("com.github.hkirk:java-html2image:0.9")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = "1.7.22")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8", version = "1.7.22")
}