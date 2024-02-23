plugins {
  id("com.rickbusarow.mahout.kotlin-jvm-module")
  id("com.dropbox.dependency-guard") version "0.4.3"
  idea
}

val compilerVersionAttribute: Attribute<String> =
  Attribute.of(
    "com.rickbusarow.kmpc.compilerVersion",
    String::class.java
  )

mahout {
  variants {
    variant("kotlin1810") {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.10")
      }
      capabilities {
        add("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.10")
      }
    }
    variant("kotlin1820") {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.20")
      }
      capabilities {
        add("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.20")
      }
    }
    variant("kotlin190") {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
      }
      capabilities {
        add("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
      }
    }
    variant("kotlin1910") {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.10")
      }
      capabilities { add("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.10") }
    }
    variant("kotlin1922") {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.22")
      }
      capabilities { add("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.22") }
    }
  }
}

/*
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
    "kotlin1922CompileClasspath",
    "kotlin1922RuntimeClasspath",
    "kotlin1922TestCompileClasspath",
    "kotlin1922TestRuntimeClasspath"
  ).forEach { cfg ->
    configuration(cfg)
  }
}
*/
