package com.rickbusarow.kmpc

// expect class KotlinEnvironment() {
//
//   private val classpathFiles: Collection<File>
//   private val sourceDirs: Collection<File>
//   private val kotlinLanguageVersion: LanguageVersion
//   private val jvmTarget: JvmTarget
//   val messageCollector: MessageCollector
// }

data class KotlinElement(
  val name: String,
  val text: String
)
