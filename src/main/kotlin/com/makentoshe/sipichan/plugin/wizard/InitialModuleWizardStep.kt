package com.makentoshe.sipichan.plugin.wizard

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.makentoshe.sipichan.plugin.StringsBundle
import javax.swing.*
import javax.swing.event.ListSelectionEvent

// https://github.com/JetBrains/intellij-community/blob/master/plugins/gradle/java/src/service/project/wizard/GradleModuleWizardStep.form
// https://github.com/JetBrains/intellij-community/blob/master/plugins/gradle/java/src/service/project/wizard/GradleModuleWizardStep.java
class InitialModuleWizardStep(
    private val context: WizardContext,
    private val disposable: Disposable
) : ModuleWizardStep() {

    private lateinit var root: JPanel

    // Model list described in form file
    private lateinit var list: JList<String>
    private lateinit var listOptionDescriptionLabel: JLabel

    // TODO(feat): specify template choose ui
    override fun getComponent(): JComponent {
        list.model = SpaceTemplateListModel()
        list.addListSelectionListener(::onListSelectionEvent)
        list.selectedIndex = 0
        return root
    }

    override fun updateDataModel() {}

    private fun onListSelectionEvent(event: ListSelectionEvent) {
        if (event.valueIsAdjusting) return
        listOptionDescriptionLabel.text = when(list.selectedIndex) {
            0 -> StringsBundle.string("space.wizard.template.empty.description")
            1 -> StringsBundle.string("space.wizard.template.chatbot.description")
            2 -> StringsBundle.string("space.wizard.template.slashcommand.description")
            else -> throw IllegalArgumentException()
        }
    }
}

class SpaceTemplateListModel: AbstractListModel<String>() {

    override fun getElementAt(index: Int): String = when(index) {
        0 -> StringsBundle.string("space.wizard.template.empty.title")
        1 -> StringsBundle.string("space.wizard.template.chatbot.title")
        2 -> StringsBundle.string("space.wizard.template.slashcommand.title")
        else -> throw IllegalArgumentException()
    }

    override fun getSize() = 3
}

