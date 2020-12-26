package com.makentoshe.sipichan.plugin.wizard

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.makentoshe.sipichan.plugin.wizard.step.FirstModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.step.SecondModuleWizardStep
import java.io.File

class SpaceModuleBuilder : ModuleBuilder() {

    // TODO add attributes define
    private val attributes = mapOf(
        "GRADLE_GROUP_ID" to "com.makentoshe",
        "GRADLE_ARTIFACT_ID" to "sipichan",
        "GRADLE_VERSION" to "1.0.0"
    )

    override fun getModuleType(): ModuleType<*> {
        return SpaceModuleType.getInstance()
    }

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel) {
        // Check project location is defined
        val contentEntryPath = this.contentEntryPath
        if (contentEntryPath == null || StringUtil.isEmpty(contentEntryPath)) return

        // get project directory in local file system and set to ModifiableRootModel
        val contentRootDirectory = File(contentEntryPath)
        FileUtilRt.createDirectory(contentRootDirectory)
        val localFileSystem = LocalFileSystem.getInstance()
        val virtualRootDirectory = localFileSystem.refreshAndFindFileByIoFile(contentRootDirectory) ?: return
        modifiableRootModel.addContentEntry(virtualRootDirectory)

        // receive project and module
        val project = modifiableRootModel.project
        val module = modifiableRootModel.module
        println(project)
        println(module)
        setupBuildGradleFile(virtualRootDirectory)
        setupSettingsGradleFile(virtualRootDirectory)
        setupGradlePropertiesFile(virtualRootDirectory)
    }

    override fun createWizardSteps(
        wizardContext: WizardContext,
        modulesProvider: ModulesProvider
    ): Array<ModuleWizardStep> {
        return arrayOf(FirstModuleWizardStep(wizardContext, modulesProvider), SecondModuleWizardStep())
    }

    override fun getCustomOptionsStep(context: WizardContext, parentDisposable: Disposable): ModuleWizardStep {
        return InitialSpaceModuleWizardStep(context, parentDisposable)
    }

    private fun setupBuildGradleFile(projectRoot: VirtualFile) {
        SpaceFileTemplate.Factory(SpaceFileTemplate.BuildGradleTemplate, attributes)
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
}
