package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.BuildConfiguration
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import javax.swing.*

class BuildModuleWizardStep(private val wizard: SpaceWizard) : ModuleWizardStep() {

    private lateinit var panel: JPanel

    private val buildRadioGroup = ButtonGroup()
    private lateinit var gradleRadioButton: JRadioButton

    private lateinit var groupTextField: JTextField
    private lateinit var artifactTextField: JTextField
    private lateinit var versionTextField: JTextField

    init {
        buildRadioGroup.add(gradleRadioButton)
    }

    override fun getComponent(): JComponent {
        return panel
    }

    override fun updateDataModel() {
        wizard.buildConfiguration = when (buildRadioGroup.selection) {
            gradleRadioButton.model -> gradleBuildConfiguration()
            else -> throw IllegalStateException()
        }
    }

    private fun gradleBuildConfiguration() = BuildConfiguration(
        BuildConfiguration.BuildSystem.GRADLE, groupTextField.text, artifactTextField.text, versionTextField.text
    )
}