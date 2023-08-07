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
    implementation("com.github.hkirk:java-html2image:Tag")
}