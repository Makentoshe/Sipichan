package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import javax.swing.JComponent
import javax.swing.JLabel

//TODO step for defining build system, group, artifact and version (may be merge with initial step)
class SecondModuleWizardStep(private val wizard: SpaceWizard) : ModuleWizardStep() {

    override fun getComponent(): JComponent {
        return JLabel("Put your descriptionPanel here (Step 2)")
    }

    override fun updateDataModel() {
        wizard.buildSystem = SpaceWizard.BuildSystem.Gradle("com.makentoshe", "sipichan", "1.0.0")
    }
}