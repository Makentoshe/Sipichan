package com.makentoshe.sipichan.plugin.wizard

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel

// https://github.com/JetBrains/intellij-community/blob/master/plugins/gradle/java/src/service/project/wizard/GradleModuleWizardStep.form
// https://github.com/JetBrains/intellij-community/blob/master/plugins/gradle/java/src/service/project/wizard/GradleModuleWizardStep.java
class InitialModuleWizardStep(
    private val context: WizardContext,
    private val disposable: Disposable
) : ModuleWizardStep() {

    private lateinit var root: JPanel
    private lateinit var list: JList<String>
    private lateinit var listOptionDescriptionLabel: JLabel

    // TODO(feat): specify template choose ui
    override fun getComponent(): JComponent {
        list.selectedIndex = 0
        return root
    }

    override fun updateDataModel() {}
}
