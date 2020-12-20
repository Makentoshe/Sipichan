package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import javax.swing.JComponent
import javax.swing.JLabel

class FirstModuleWizardStep(
    private val context: WizardContext,
    private val provider: ModulesProvider
) : ModuleWizardStep() {

    override fun getComponent(): JComponent {
        return JLabel("Put your content here (Step 1)")
    }

    override fun updateDataModel() {}
}