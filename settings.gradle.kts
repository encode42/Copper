rootProject.name = "Copper"

include("copper-bom", "copper-core", "copper-slf4j", "copper-jda")

includeMinecraft("copper-bukkit", "copper-cloud")

fun includeMinecraft(vararg modules: String) {
    for (module in modules) {
        includeSubproject(module, file("copper-minecraft/$module"))
    }
}

fun includeSubproject(module: String, file: File) {
    include(module)
    project(":$module").projectDir = file
}
