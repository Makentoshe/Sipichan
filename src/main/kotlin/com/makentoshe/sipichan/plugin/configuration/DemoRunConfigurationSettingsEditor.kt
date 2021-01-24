package com.makentoshe.sipichan.plugin.configuration

import com.intellij.openapi.options.SettingsEditor
import javax.swing.JComponent
import javax.swing.JPanel

class DemoRunConfigurationSettingsEditor: SettingsEditor<DemoRunConfiguration>() {
    private lateinit var panel: JPanel

    override fun resetEditorFrom(s: DemoRunConfiguration) {

    }

    override fun applyEditorTo(s: DemoRunConfiguration) {

    }

    override fun createEditor(): JComponent {
        return panel
    }

}