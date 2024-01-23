package com.rickbusarow.kmpc

import org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM

actual fun thing(): String {
    return TopDownAnalyzerFacadeForJVM.toString()
}
