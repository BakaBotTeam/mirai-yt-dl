plugins {
    val kotlinVersion = "1.9.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.15.0"
}

group = "ltd.guimc.mirai.ytdl"
version = "0.1.0"

dependencies {
    val overflowVersion = "2.16.0-db61867-SNAPSHOT"
    compileOnly("top.mrxiaom:overflow-core-api:$overflowVersion")
    compileOnly("top.mrxiaom:overflow-core:$overflowVersion")
}

repositories {
    if (System.getenv("CI")?.toBoolean() != true) {
        maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    }
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    mavenCentral()
}
