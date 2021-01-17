package com.makentoshe.sipichan.plugin.wizard

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.makentoshe.sipichan.plugin.wizard.step.FirstModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.step.SecondModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.strategy.SpaceModuleBuilderStrategy
import java.io.File

class SpaceModuleBuilder(
    private val wizardBuilder: SpaceWizard2.Builder,
    private val strategy: SpaceModuleBuilderStrategy
) : ModuleBuilder() {

    override fun getModuleType(): ModuleType<*> {
        return SpaceModuleType.getInstance()
    }

    override fun createWizardSteps(
        wizardContext: WizardContext,
        modulesProvider: ModulesProvider
    ): Array<ModuleWizardStep> {
        return arrayOf(FirstModuleWizardStep(wizardBuilder), SecondModuleWizardStep(wizardBuilder))
    }

    override fun getCustomOptionsStep(context: WizardContext, parentDisposable: Disposable): ModuleWizardStep {
        return InitialSpaceModuleWizardStep(context, parentDisposable, wizardBuilder)
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

        println(wizardBuilder.buildSystem)
        println(wizardBuilder.projectType)

        strategy.setupRootModel(modifiableRootModel, virtualRootDirectory)
    }

    override fun setupModule(module: Module) {
        super.setupModule(module)
        strategy.setupModule(module)
    }
}
