package com.makentoshe.sipichan.plugin.wizard

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import javax.swing.JComponent
import javax.swing.JLabel

class FirstModuleWizardStep : ModuleWizardStep() {
    override fun getComponent(): JComponent {
        return JLabel("Put your content here (Step 1)")
    }

    override fun updateDataModel() {}
}