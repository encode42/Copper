repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    api(project(":copper-core"))
    compileOnly(group = "io.papermc.paper", name = "paper-api", version = "1.18.2-R0.1-SNAPSHOT")
}
