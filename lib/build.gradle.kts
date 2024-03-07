plugins {
  kotlin("multiplatform")
  // id("com.dropbox.dependency-guard")
}

group = "com.rickbusarow.kmpc"
version = "0.1.0-SNAPSHOT"

val compilerVersionAttribute: Attribute<String> =
  Attribute.of("com.rickbusarow.kmpc.compilerVersion", String::class.java)

kotlin {

  jvmToolchain(11)

  // jvm("kotlin1810") { attributes.attribute(compilerVersionAttribute, name) }
  // jvm("kotlin1820") { attributes.attribute(compilerVersionAttribute, name) }
  // jvm("kotlin190") { attributes.attribute(compilerVersionAttribute, name) }
  // jvm("kotlin1910") { attributes.attribute(compilerVersionAttribute, name) }
  // jvm("kotlin1920") { attributes.attribute(compilerVersionAttribute, name) }

  jvm("commonJvm") {

    // withJava()

    // val compat1 by compilations.registering
    // val compat2 by compilations.registering

    println("################################################")
    println(compilations.names)
    println("################################################")
  }

  sourceSets {
    val commonMain by getting
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val commonJvm by creating {
      dependsOn(commonMain)
    }

    // val commonJvmCompat1 by getting { dependsOn(commonJvm) }
    // val commonJvmCompat2 by getting { dependsOn(commonJvm) }

    // java {
    //
    // val boogers by java.sourceSets.registering {
    //   java.srcDir("src/boogers/kotlin")
    // }
    //
    // registerFeature("boogers") rf@{
    //   val feature: FeatureSpec = this@rf
    //
    //   println("############################### java source sets")
    //   println(sourceSets.names)
    //   println("###############################")
    //
    //   feature.usingSourceSet(boogers.get())
    // }
    // }

    // val kotlin1810Main by getting {
    //   dependencies {
    //     api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.10")
    //   }
    // }
    // val kotlin1810Test by getting
    // val kotlin1820Main by getting {
    //   dependencies {
    //     api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.20")
    //   }
    // }
    // val kotlin1820Test by getting
    // val kotlin190Main by getting {
    //   dependencies {
    //     api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
    //   }
    // }
    // val kotlin190Test by getting
    // val kotlin1910Main by getting {
    //   dependencies {
    //     api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.10")
    //   }
    // }
    // val kotlin1910Test by getting
    // val kotlin1920Main by getting {
    //   dependencies {
    //     api("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.20")
    //   }
    // }
    // val kotlin1920Test by getting
  }
}

// dependencyGuard {
//   listOf(
//     "kotlin1810CompileClasspath",
//     "kotlin1810RuntimeClasspath",
//     "kotlin1810TestCompileClasspath",
//     "kotlin1810TestRuntimeClasspath",
//     "kotlin1820CompileClasspath",
//     "kotlin1820RuntimeClasspath",
//     "kotlin1820TestCompileClasspath",
//     "kotlin1820TestRuntimeClasspath",
//     "kotlin190CompileClasspath",
//     "kotlin190RuntimeClasspath",
//     "kotlin190TestCompileClasspath",
//     "kotlin190TestRuntimeClasspath",
//     "kotlin1910CompileClasspath",
//     "kotlin1910RuntimeClasspath",
//     "kotlin1910TestCompileClasspath",
//     "kotlin1910TestRuntimeClasspath",
//     "kotlin1920CompileClasspath",
//     "kotlin1920RuntimeClasspath",
//     "kotlin1920TestCompileClasspath",
//     "kotlin1920TestRuntimeClasspath"
//   ).forEach { cfg ->
//     configuration(cfg)
//   }
// }
