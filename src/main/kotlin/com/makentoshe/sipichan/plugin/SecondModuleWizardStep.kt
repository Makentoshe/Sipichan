package com.makentoshe.sipichan.plugin

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import javax.swing.JComponent
import javax.swing.JLabel

class SecondModuleWizardStep : ModuleWizardStep() {
    override fun getComponent(): JComponent {
        return JLabel("Put your content here (Step 2)")
    }

    override fun updateDataModel() {}
}