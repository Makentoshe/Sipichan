package com.makentoshe.sipichan.plugin.wizard.strategy

import com.intellij.ide.util.EditorHelper
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.externalSystem.importing.ImportSpecBuilder
import com.intellij.openapi.externalSystem.model.ProjectSystemId
import com.intellij.openapi.externalSystem.service.execution.ProgressExecutionMode.MODAL_SYNC
import com.intellij.openapi.externalSystem.service.project.manage.ExternalProjectsManagerImpl
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.makentoshe.sipichan.plugin.wizard.SpaceFileTemplate
import java.io.File
import java.nio.file.Path

class GradleSpaceModuleBuilderStrategy : SpaceModuleBuilderStrategy {

    // TODO add attributes define
    private val attributes = mapOf(
        "GRADLE_GROUP_ID" to "com.makentoshe",
        "GRADLE_ARTIFACT_ID" to "sipichan",
        "GRADLE_VERSION" to "1.0.0"
    )

    private lateinit var virtualRootDirectory: VirtualFile
    private lateinit var buildGradleFile: VirtualFile

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel, virtualRootDirectory: VirtualFile) {
        this.virtualRootDirectory = virtualRootDirectory

        setupBuildGradleFile(virtualRootDirectory)
        setupSettingsGradleFile(virtualRootDirectory)
        setupGradlePropertiesFile(virtualRootDirectory)
        setupGradleBinaries(virtualRootDirectory)
    }

    private fun setupBuildGradleFile(projectRoot: VirtualFile) {
        buildGradleFile = SpaceFileTemplate.Factory(SpaceFileTemplate.BuildGradleTemplate, attributes)
            .create("build.gradle", projectRoot)
    }

    private fun setupSettingsGradleFile(projectRoot: VirtualFile) {
        SpaceFileTemplate.Factory(SpaceFileTemplate.SettingsGradleTemplate, attributes)
            .create("settings.gradle", projectRoot)
    }

    private fun setupGradlePropertiesFile(projectRoot: VirtualFile) {
        SpaceFileTemplate.Factory(SpaceFileTemplate.GradlePropertiesTemplate, attributes)
            .create("gradle.properties", projectRoot)
    }

    private fun setupGradleBinaries(projectRoot: VirtualFile) {
        setupGradleBinary(projectRoot, "gradlew.bat")
        setupGradleBinary(projectRoot, "gradlew")
        setupGradleBinary(projectRoot, "gradle/wrapper/gradle-wrapper.jar")
        setupGradleBinary(projectRoot, "gradle/wrapper/gradle-wrapper.properties")
    }

    private fun setupGradleBinary(projectRoot: VirtualFile, location: String) {
        val stream = javaClass.classLoader.getResourceAsStream("/binaries/gradle/$location")
        val file = File(projectRoot.toNioPath().toFile(), location)
        file.parentFile?.mkdirs()
        assert(file.createNewFile())
        file.writeBytes(stream!!.readBytes())
    }

    override fun setupModule(module: Module) {
        ApplicationManager.getApplication().invokeLater({
            loadPreviewProject(module)
            openFileOnStartup(module, buildGradleFile)
            reloadProject(module)
        }, ModalityState.NON_MODAL, module.project.disposed)
    }

    private fun loadPreviewProject(module: Module) {
        val previewSpec = ImportSpecBuilder(module.project, ProjectSystemId.findById("GRADLE")!!)
        previewSpec.usePreviewMode()
        previewSpec.use(MODAL_SYNC)
        previewSpec.callback(ImportSpecBuilder.DefaultProjectRefreshCallback(previewSpec.build()))
        ExternalSystemUtil.refreshProject(virtualRootDirectory.toNioPath().systemIndependentPath, previewSpec)
    }

    private fun openFileOnStartup(module: Module, file: VirtualFile) {
        val psiManager = PsiManager.getInstance(module.project)
        val psiFile = psiManager.findFile(file)
        if (psiFile != null) EditorHelper.openInEditor(psiFile)
    }

    private fun reloadProject(module: Module) {
        ExternalProjectsManagerImpl.getInstance(module.project).runWhenInitialized {
            val importSpec = ImportSpecBuilder(module.project, ProjectSystemId.findById("GRADLE")!!)
            // TODO importSpec.createDirectoriesForEmptyContentRoots()
            importSpec.callback(ImportSpecBuilder.DefaultProjectRefreshCallback(importSpec.build()))
            ExternalSystemUtil.refreshProject(virtualRootDirectory.toNioPath().systemIndependentPath, importSpec)
        }
    }

    private val Path.systemIndependentPath: String
        get() = toString().replace(File.separatorChar, '/')
}
