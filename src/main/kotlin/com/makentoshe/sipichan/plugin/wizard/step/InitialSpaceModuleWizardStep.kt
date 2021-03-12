package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.makentoshe.sipichan.plugin.StringsBundle
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import com.makentoshe.sipichan.plugin.wizard.model.SpaceTemplateListModel
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.event.ListSelectionEvent

// https://github.com/JetBrains/intellij-community/blob/master/plugins/gradle/java/src/service/project/wizard/GradleModuleWizardStep.form
// https://github.com/JetBrains/intellij-community/blob/master/plugins/gradle/java/src/service/project/wizard/GradleModuleWizardStep.java
class InitialSpaceModuleWizardStep(
    private val context: WizardContext,
    private val disposable: Disposable,
    private val wizard: SpaceWizard
) : ModuleWizardStep() {

    private lateinit var root: JPanel

    // Model list described in form file
    private lateinit var list: JList<String>

    // Bottom panel with template descriptionPanel
    private lateinit var listOptionDescriptionLabel: JLabel

    // TODO improve template specify choose
    override fun getComponent(): JComponent {
        list.model = SpaceTemplateListModel()
        list.addListSelectionListener(::onListSelectionEvent)
        list.selectedIndex = 0
        return root
    }

    override fun updateDataModel() {}

    private fun onListSelectionEvent(event: ListSelectionEvent) {
        if (event.valueIsAdjusting) return

        listOptionDescriptionLabel.text = when (list.selectedIndex) {
            0 -> {
                wizard.projectType = SpaceWizard.ProjectType.BLANK
                StringsBundle.string("space.wizard.template.blank.description")
            }
            1 -> {
                wizard.projectType = SpaceWizard.ProjectType.CHATBOT
                StringsBundle.string("space.wizard.template.chatbot.description")
            }
            2 -> {
                wizard.projectType = SpaceWizard.ProjectType.CLIENT
                StringsBundle.string("space.wizard.template.client.description")
            }
            else -> throw IllegalArgumentException()
        }
    }
}

