package com.makentoshe.sipichan.plugin

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import javax.swing.JComponent
import javax.swing.JLabel

class InitialModuleWizardStep(
    private val context: WizardContext,
    private val disposable: Disposable
) : ModuleWizardStep() {

    override fun getComponent(): JComponent {
        return JLabel("Provide some settings here")
    }

    override fun updateDataModel() {}
}