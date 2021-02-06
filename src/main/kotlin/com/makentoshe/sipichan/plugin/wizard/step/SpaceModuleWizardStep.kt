package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JProgressBar
import javax.swing.JTextField


// TODO step for defining space constants (client id, client secret, verification token, etc)
class SpaceModuleWizardStep(private val wizard: SpaceWizard) : ModuleWizardStep() {

    private lateinit var panel: JPanel
    private lateinit var urlSpaceInstanceTextField: JTextField
    private lateinit var networkCheckProgressBar: JProgressBar

    override fun getComponent(): JComponent {
        networkCheckProgressBar.isIndeterminate = true
        return panel//JLabel("Put your content here (Step 1)")
    }

    // called on next step
    override fun updateDataModel() = Unit
}
