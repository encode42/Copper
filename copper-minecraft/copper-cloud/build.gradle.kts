repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    api(project(":copper-bukkit"))
    api(group = "net.kyori", name = "adventure-platform-bukkit", version = "4.1.0")
    implementation(platform("cloud.commandframework:cloud-bom:1.6.2"))
    api(group = "cloud.commandframework", name = "cloud-core")
    api(group = "cloud.commandframework", name = "cloud-paper")
    api(group = "cloud.commandframework", name = "cloud-minecraft-extras",)
    api(group = "cloud.commandframework", name = "cloud-annotations")
    compileOnly(group = "io.papermc.paper", name = "paper-api", version = "1.18.2-R0.1-SNAPSHOT")
}
