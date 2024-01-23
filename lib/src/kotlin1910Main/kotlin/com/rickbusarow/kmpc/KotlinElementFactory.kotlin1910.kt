package com.rickbusarow.kmpc

actual class KotlinElementFactory {
  actual fun create(
    disposable: Disposable
  ): KotlinElement {
    TODO("Not yet implemented")
  }
}

actual interface Disposable {
  actual fun dispose()

  actual interface Parent : Disposable {
    actual fun beforeTreeDispose()
  }
}
