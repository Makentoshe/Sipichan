package com.makentoshe.sipichan.plugin.wizard.strategy

import com.intellij.ide.util.EditorHelper
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.externalSystem.importing.ImportSpecBuilder
import com.intellij.openapi.externalSystem.model.DataNode
import com.intellij.openapi.externalSystem.model.ProjectSystemId
import com.intellij.openapi.externalSystem.model.project.ProjectData
import com.intellij.openapi.externalSystem.service.execution.ProgressExecutionMode
import com.intellij.openapi.externalSystem.service.project.ExternalProjectRefreshCallback
import com.intellij.openapi.externalSystem.service.project.ProjectDataManager
import com.intellij.openapi.externalSystem.service.project.manage.ExternalProjectsManagerImpl
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.makentoshe.sipichan.plugin.wizard.BuildConfiguration
import com.makentoshe.sipichan.plugin.wizard.source.ProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.template.FilesTemplate
import com.makentoshe.sipichan.plugin.wizard.template.GradleFilesTemplate
import java.io.File
import java.nio.file.Path

class GradleSpaceModuleBuilderStrategy(
    private val buildConfiguration: BuildConfiguration,
    private val sourceProvider: ProjectSourceProvider
) : SpaceModuleBuilderStrategy {

    private lateinit var virtualRootDirectory: VirtualFile
    private lateinit var buildGradleFile: VirtualFile

    override fun setupRootModel(
        modifiableRootModel: ModifiableRootModel,
        virtualRootDirectory: VirtualFile
    ) {
        this.virtualRootDirectory = virtualRootDirectory

        setupBuildGradleFile(virtualRootDirectory)
        setupSettingsGradleFile(virtualRootDirectory)
        setupGradlePropertiesFile(virtualRootDirectory)
        setupGradleBinaries(virtualRootDirectory)

        sourceProvider.buildProjectSourceFiles(modifiableRootModel, virtualRootDirectory)
    }

    private fun setupBuildGradleFile(projectRoot: VirtualFile) {
        buildGradleFile = FilesTemplate.Factory(GradleFilesTemplate.BuildGradleTemplate, buildConfiguration.attributes())
            .create("build.gradle", projectRoot)
    }

    private fun setupSettingsGradleFile(projectRoot: VirtualFile) {
        FilesTemplate.Factory(GradleFilesTemplate.SettingsGradleTemplate, buildConfiguration.attributes())
            .create("settings.gradle", projectRoot)
    }

    private fun setupGradlePropertiesFile(projectRoot: VirtualFile) {
        FilesTemplate.Factory(GradleFilesTemplate.GradlePropertiesTemplate, buildConfiguration.attributes())
            .create("gradle.properties", projectRoot)
    }

    private fun setupGradleBinaries(projectRoot: VirtualFile) {
        setupGradleBinary(projectRoot, "gradlew.bat")
        setupGradleBinary(projectRoot, "gradlew")
        setupGradleBinary(projectRoot, "gradle/wrapper/gradle-wrapper.jar")
        setupGradleBinary(projectRoot, "gradle/wrapper/gradle-wrapper.properties")
    }

    private fun setupGradleBinary(projectRoot: VirtualFile, location: String) {
        val stream = javaClass.classLoader.getResourceAsStream("/templates/gradle/$location")
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
        previewSpec.use(ProgressExecutionMode.MODAL_SYNC)
        ExternalSystemUtil.refreshProject(virtualRootDirectory.toNioPath().systemIndependentPath, previewSpec)
    }

    private fun openFileOnStartup(module: Module, file: VirtualFile) {
        val psiManager = PsiManager.getInstance(module.project)
        val psiFile = psiManager.findFile(file)
        if (psiFile != null) EditorHelper.openInEditor(psiFile)
    }

    private fun reloadProject(module: Module) {
        // TODO check Gradle...Action for importing gradle tasks
        ExternalProjectsManagerImpl.getInstance(module.project).runWhenInitialized {
            ExternalSystemUtil.ensureToolWindowContentInitialized(module.project, ProjectSystemId.findById("GRADLE")!!)
            val importSpec = ImportSpecBuilder(module.project, ProjectSystemId.findById("GRADLE")!!)
            importSpec.callback(FinalImportCallback(module.project))
            ExternalSystemUtil.refreshProject(virtualRootDirectory.toNioPath().systemIndependentPath, importSpec)
        }
    }

    private val Path.systemIndependentPath: String
        get() = toString().replace(File.separatorChar, '/')

    internal class FinalImportCallback(private val project: Project) : ExternalProjectRefreshCallback {
        override fun onSuccess(externalProject: DataNode<ProjectData>?) {
            if (externalProject == null) return
            ProjectDataManager.getInstance().importData(externalProject, project, false)
        }
    }
}