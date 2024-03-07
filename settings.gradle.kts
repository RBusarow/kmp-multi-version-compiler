pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
  }
  plugins {
    kotlin("multiplatform") version "2.0.0-Beta4" apply false
    kotlin("jvm") version "2.0.0-Beta4" apply false
    id("com.dropbox.dependency-guard") version "0.5.0" apply false
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

dependencyResolutionManagement {

  repositories {
    mavenCentral()
    google()
  }
}

rootProject.name = "kmp-compiler"

include(":lib")
