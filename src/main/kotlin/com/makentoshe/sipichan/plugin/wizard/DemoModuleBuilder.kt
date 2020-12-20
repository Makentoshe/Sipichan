package com.makentoshe.sipichan.plugin.wizard

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider

class DemoModuleBuilder : ModuleBuilder() {

    override fun getModuleType(): ModuleType<*> {
        return DemoModuleType.getInstance()
    }

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel) {
        super.setupRootModel(modifiableRootModel)
    }

    override fun createWizardSteps(
        wizardContext: WizardContext,
        modulesProvider: ModulesProvider
    ): Array<ModuleWizardStep> {
        return arrayOf(FirstModuleWizardStep(), SecondModuleWizardStep())
    }

    override fun getCustomOptionsStep(context: WizardContext, parentDisposable: Disposable): ModuleWizardStep {
        return InitialModuleWizardStep(context, parentDisposable)
    }

}

