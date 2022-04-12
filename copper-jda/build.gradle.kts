repositories {
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    api(project(":copper-slf4j"))
    api(group = "net.dv8tion", name = "JDA", version = "4.4.0_350") {
        exclude(module = "opus-java")
    }
}
