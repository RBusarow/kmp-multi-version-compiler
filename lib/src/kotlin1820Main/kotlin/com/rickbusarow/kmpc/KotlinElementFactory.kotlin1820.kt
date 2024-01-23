package com.rickbusarow.kmpc

import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.config.addKotlinSourceRoots
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles.JVM_CONFIG_FILES
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.compiler.NoScopeRecordCliBindingTrace
import org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM
import org.jetbrains.kotlin.cli.jvm.compiler.report
import org.jetbrains.kotlin.cli.jvm.config.addJavaSourceRoots
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.cli.jvm.config.addJvmSdkRoots
import org.jetbrains.kotlin.cli.jvm.modules.CoreJrtFileSystem
import org.jetbrains.kotlin.config.ApiVersion
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys
import org.jetbrains.kotlin.config.JvmTarget
import org.jetbrains.kotlin.config.LanguageVersion
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl
import org.jetbrains.kotlin.config.languageVersionSettings
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl
import org.jetbrains.kotlin.incremental.isJavaFile
import org.jetbrains.kotlin.incremental.isKotlinFile
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.lazy.declarations.FileBasedDeclarationProviderFactory
import org.jetbrains.kotlin.utils.PathUtil
import java.io.File

actual class KotlinElementFactory {
  actual fun create(): KotlinElement {
    TODO("Not yet implemented")
  }

  private fun createAnalysisResult(
    coreEnvironment: KotlinCoreEnvironment,
    ktFiles: List<KtFile>,
    dependencyModuleDescriptors: List<ModuleDescriptorImpl>
  ): AnalysisResult {

    val analyzer =
      AnalyzerWithCompilerReport(
        messageCollector = messageCollector,
        languageVersionSettings = coreEnvironment.configuration.languageVersionSettings,
        renderDiagnosticName = true
      )

    analyzer.analyzeAndReport(ktFiles) {
      TopDownAnalyzerFacadeForJVM.analyzeFilesWithJavaIntegration(
        project = coreEnvironment.project,
        files = ktFiles,
        trace = NoScopeRecordCliBindingTrace(),
        configuration = coreEnvironment.configuration,
        packagePartProvider = coreEnvironment::createPackagePartProvider,
        declarationProviderFactory = org
          .jetbrains
          .kotlin
          .resolve
          .lazy
          .declarations::FileBasedDeclarationProviderFactory,
        explicitModuleDependencyList = dependencyModuleDescriptors
      )
    }

    return analyzer.analysisResult
  }

  val kotlinExtensions = listOf("kt", "kts")

  private fun createCompilerConfiguration(
    sourceFiles: List<File>,
    kotlinLanguageVersion: LanguageVersion,
    jvmTarget: JvmTarget
  ): CompilerConfiguration {
    val javaFiles = mutableListOf<File>()
    val kotlinFiles = mutableListOf<String>()

    sourceFiles.forEach { file ->
      when {
        file.isKotlinFile(kotlinExtensions) -> kotlinFiles.add(file.absolutePath)
        file.isJavaFile() -> javaFiles.add(file)
      }
    }

    val kotlinExtensions = listOf("kt", "kts")

    return CompilerConfiguration().apply {
      put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
      put(JVMConfigurationKeys.JVM_TARGET, jvmTarget)
      put(CommonConfigurationKeys.MODULE_NAME, "kable")

      val languageVersionSettings =
        LanguageVersionSettingsImpl(
          languageVersion = kotlinLanguageVersion,
          apiVersion = ApiVersion.createByLanguageVersion(kotlinLanguageVersion)
        )
      put(CommonConfigurationKeys.LANGUAGE_VERSION_SETTINGS, languageVersionSettings)

      addJavaSourceRoots(javaFiles)
      addKotlinSourceRoots(kotlinFiles)
      addJvmClasspathRoots(emptyList())
      configureJdkClasspathRoots()
    }
  }

  private fun CompilerConfiguration.configureJdkClasspathRoots() {
    if (getBoolean(JVMConfigurationKeys.NO_JDK)) return

    val jdkHome = get(JVMConfigurationKeys.JDK_HOME)
    val (javaRoot, classesRoots) =
      if (jdkHome == null) {
        val javaHome = File(System.getProperty("java.home"))
        put(JVMConfigurationKeys.JDK_HOME, javaHome)

        javaHome to PathUtil.getJdkClassesRootsFromCurrentJre()
      } else {
        jdkHome to PathUtil.getJdkClassesRoots(jdkHome)
      }

    if (!CoreJrtFileSystem.isModularJdk(javaRoot)) {
      if (classesRoots.isEmpty()) {
        report(CompilerMessageSeverity.ERROR, "No class roots are found in the JDK path: $javaRoot")
      } else {
        addJvmSdkRoots(classesRoots)
      }
    }
  }

  private fun createKotlinCoreEnvironment(
    configuration: CompilerConfiguration,
    disposable: Disposable
  ): KotlinCoreEnvironment {
    // https://github.com/JetBrains/kotlin/commit/2568804eaa2c8f6b10b735777218c81af62919c1
    setIdeaIoUseFallback()

    return KotlinCoreEnvironment.createForProduction(
      disposable,
      configuration,
      JVM_CONFIG_FILES
    )
  }
}

actual interface Disposable : org.jetbrains.kotlin.com.intellij.openapi.Disposable {
  actual override fun dispose()

  actual interface Parent :
    Disposable,
    org.jetbrains.kotlin.com.intellij.openapi.Disposable.Parent {
    actual override fun beforeTreeDispose()
  }
}
