import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val javaVersion = 17

allprojects {
    group = "dev.encode42.copper"
    version = "2.1.0-SNAPSHOT"
    description = "Java API held together by tape"

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

subprojects {
    if (name == "copper-bom") {
        apply(plugin = "java-platform")
    } else {
        apply(plugin = "java-library")
    }

    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(javaVersion)

        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(javaVersion))
            }

            withJavadocJar()
            withSourcesJar()
        }
    }

    tasks.withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name()
    }

    tasks.withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            showStackTraces = true
            exceptionFormat = TestExceptionFormat.FULL
            events(TestLogEvent.STANDARD_OUT)
        }

        dependencies {
            testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.8.2")
            testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.8.2")
        }
    }

    publishing {
        repositories {
            maven {
                val releases = "https://repo.encode42.dev/releases"
                val snapshots = "https://repo.encode42.dev/snapshots"

                name = "encode42Repo"
                url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshots else releases)
                credentials(PasswordCredentials::class)
            }
        }
        publications.create<MavenPublication>("mavenJava") {
            if (project.name == "copper-bom") {
                from(components["javaPlatform"])
            } else {
                from(components["java"])
            }
        }
    }
}

dependencies {
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.8.1")
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.8.1")
}
