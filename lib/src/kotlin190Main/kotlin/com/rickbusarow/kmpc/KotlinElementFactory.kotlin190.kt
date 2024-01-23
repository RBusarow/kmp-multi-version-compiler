package com.rickbusarow.kmpc

actual class KotlinElementFactory {
  actual fun create(): KotlinElement {
    TODO("Not yet implemented")
  }
}

actual interface Disposable : org.jetbrains.kotlin.com.intellij.openapi.Disposable {
  actual override fun dispose()
  actual interface Parent : Disposable, org.jetbrains.kotlin.com.intellij.openapi.Disposable.Parent {
    actual override fun beforeTreeDispose()
  }
}
