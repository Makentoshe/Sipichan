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
import com.makentoshe.sipichan.plugin.wizard.step.FirstModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.step.SecondModuleWizardStep
import java.io.File

class SpaceModuleBuilder : ModuleBuilder() {

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
        val modelContentRootDirectory = localFileSystem.refreshAndFindFileByIoFile(contentRootDirectory) ?: return
        modifiableRootModel.addContentEntry(modelContentRootDirectory)

        // receive project and module
        val project = modifiableRootModel.project
        val module = modifiableRootModel.module
        println(project)
        println(module)
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

}

