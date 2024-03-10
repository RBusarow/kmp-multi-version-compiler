import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
  kotlin("multiplatform")
  id("com.dropbox.dependency-guard")
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

  jvm {

    val target: KotlinJvmTarget = this@jvm

    // withJava()

    val mainCompilation = target.compilations.getByName("main")

    val mainKotlinSourceSet = mainCompilation.defaultSourceSet

    // val shared by kotlin.sourceSets.creating { /* kotlin.srcDirs("src/shared/kotlin") */ }

    val compat1822 by compilations.creating { /*defaultSourceSet.dependsOn(shared)*/ }
    val compat1900 by compilations.creating { /*defaultSourceSet.dependsOn(shared)*/ }
    val compat1910 by compilations.creating { /*defaultSourceSet.dependsOn(shared)*/ }
    val compat1920 by compilations.creating { /*defaultSourceSet.dependsOn(shared)*/ }
    val compat1923 by compilations.creating { /*defaultSourceSet.dependsOn(shared)*/ }

    println(
      """
      |################################################
      | -- compilations
      |${compilations.namesLines()}
      |
      | -- kotlin sourceSets
      |${kotlin.sourceSets.joinToString("\n") { it.name + " -- ${it.dependsOn.map { it.name }}" }}
      |
      | -- java sourceSets
      |${java.sourceSets.namesLines()}
      |################################################
      """.trimMargin()
    )

    compilations.forEach {
      it.defaultSourceSet
        .kotlin
        .srcDirs
        .forEach { it.resolve("com/rickbusarow/kmpc").mkdirs() }
    }
  }

  println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% targets: ${kotlin.targets.names}")

  sourceSets {
    val commonMain by getting
  }
}

dependencyGuard {
  configurations.names
    .filter { name ->
      name.endsWith("CompileClasspath") ||
        name.endsWith("RuntimeClasspath")
    }.forEach { cfg ->
      configuration(cfg)
    }
}

fun NamedDomainObjectCollection<*>.namesLines() = names.joinToString("\n")
