import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("multiplatform") version "1.9.21"
  id("com.dropbox.dependency-guard") version "0.4.3"
}

group = "com.rickbusarow.kmpc"
version = "0.1.0-SNAPSHOT"

val compilerVersionAttribute: Attribute<String> =
  Attribute.of(
    "com.rickbusarow.kmpc.compilerVersion",
    String::class.java
  )

kotlin {
  jvm("kotlin1810") { attributes.attribute(compilerVersionAttribute, name) }
  jvm("kotlin1820") { attributes.attribute(compilerVersionAttribute, name) }
  jvm("kotlin190") { attributes.attribute(compilerVersionAttribute, name) }
  jvm("kotlin1910") { attributes.attribute(compilerVersionAttribute, name) }
  jvm("kotlin1920") { attributes.attribute(compilerVersionAttribute, name) }

  targets.withType<KotlinJvmTarget> {
    jvmToolchain(11)
    // withJava()
    testRuns.configureEach {
      executionTask.configure { useJUnitPlatform() }
    }
  }

  tasks.withType(KotlinCompile::class.java).configureEach {
    kotlinOptions {
      allWarningsAsErrors = false

      // val kotlinMajor = libs.versions.kotlinApi.get()
      // languageVersion = kotlinMajor
      // apiVersion = kotlinMajor

      // jvmTarget = libs.versions.jvmTarget.get()

      freeCompilerArgs +=
        listOf(
          "-opt-in=kotlin.contracts.ExperimentalContracts"
        )
    }
  }

  sourceSets {
    val commonMain by getting
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val kotlin1810Main by getting {
      dependencies {
        api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.10")
      }
    }
    val kotlin1810Test by getting
    val kotlin1820Main by getting {
      dependencies {
        api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.20")
      }
    }
    val kotlin1820Test by getting
    val kotlin190Main by getting {
      dependencies {
        api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
      }
    }
    val kotlin190Test by getting
    val kotlin1910Main by getting {
      dependencies {
        api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.10")
      }
    }
    val kotlin1910Test by getting
    val kotlin1920Main by getting {
      dependencies {
        api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.20")
      }
    }
    val kotlin1920Test by getting
  }
}

dependencyGuard {
  listOf(
    "kotlin1810CompileClasspath",
    "kotlin1810RuntimeClasspath",
    "kotlin1810TestCompileClasspath",
    "kotlin1810TestRuntimeClasspath",
    "kotlin1820CompileClasspath",
    "kotlin1820RuntimeClasspath",
    "kotlin1820TestCompileClasspath",
    "kotlin1820TestRuntimeClasspath",
    "kotlin190CompileClasspath",
    "kotlin190RuntimeClasspath",
    "kotlin190TestCompileClasspath",
    "kotlin190TestRuntimeClasspath",
    "kotlin1910CompileClasspath",
    "kotlin1910RuntimeClasspath",
    "kotlin1910TestCompileClasspath",
    "kotlin1910TestRuntimeClasspath",
    "kotlin1920CompileClasspath",
    "kotlin1920RuntimeClasspath",
    "kotlin1920TestCompileClasspath",
    "kotlin1920TestRuntimeClasspath"
  ).forEach { cfg ->
    configuration(cfg)
  }
}
