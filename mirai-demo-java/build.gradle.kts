plugins {
    id("kotlin")
    id("java")
}

val kotlinVersion: String by rootProject.ext

val miraiCoreVersion: String by rootProject.ext
val miraiJaptVersion: String by rootProject.ext

dependencies {
    implementation("net.mamoe:mirai-core:$miraiCoreVersion")
    implementation("net.mamoe:mirai-core-qqandroid:$miraiCoreVersion")
}