# Copper
Java API held together by tape

Gradle build tooling heavily inspired by [cloud](https://github.com/Incendo/cloud)'s tooling.

`copper-bom`: Bill of materials  
`copper-core`: The main module containing configuration, utilities, and more.  
`copper-slf4j`: An extension of OmniLogger with SLF4J support.  
`copper-jda`: Utilities and more for JDA.  
`copper-bukkit`: Utilities and more for Bukkit.  
`copper-cloud`: Extension of `copper-bukkit` with [cloud](https://github.com/Incendo/cloud) command framework.  

### Use with Gradle
Add the repository
```gradle
repositories {
    maven {
        name = "encode42-repo"
        url = "https://repo.encode42.dev/snapshots"
    }
}
```

Add the dependencies
```
dependencies {
    implementation(group: "dev.encode42.copper", name: "copper-bom", version: "2.0-SNAPSHOT")
    implementation(group: "dev.encode42.copper", name: "copper-<MODULE>")
}
```

Javadoc and better readme coming soon.