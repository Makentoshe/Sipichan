package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard2
import javax.swing.JComponent
import javax.swing.JLabel

// TODO step for defining space constants (client id, client secret, verification token, etc)
class FirstModuleWizardStep(private val wizardBuilder: SpaceWizard2.Builder) : ModuleWizardStep() {

    override fun getComponent(): JComponent {
        return JLabel("Put your content here (Step 1)")
    }

    override fun updateDataModel() = Unit
}