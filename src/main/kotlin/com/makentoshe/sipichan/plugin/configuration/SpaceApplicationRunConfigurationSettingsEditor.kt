package com.makentoshe.sipichan.plugin.configuration

import com.intellij.openapi.options.SettingsEditor
import javax.swing.JComponent
import javax.swing.JPanel

class SpaceApplicationRunConfigurationSettingsEditor: SettingsEditor<SpaceApplicationRunConfiguration>() {

    private lateinit var panel: JPanel

    override fun resetEditorFrom(s: SpaceApplicationRunConfiguration) {

    }

    override fun applyEditorTo(s: SpaceApplicationRunConfiguration) {

    }

    override fun createEditor(): JComponent {
        return panel
    }

}