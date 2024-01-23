package com.rickbusarow.kmpc

expect interface Disposable {
  fun dispose()

  interface Parent : Disposable {
    fun beforeTreeDispose()
  }
}

expect class KotlinElementFactory {
  fun create(disposable: Disposable): KotlinElement
}
