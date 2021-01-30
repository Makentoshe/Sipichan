package com.makentoshe.sipichan.plugin.configuration

import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.util.Key
import com.makentoshe.sipichan.plugin.configuration.model.TunnelingToolComboBoxModel
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JPanel

class SpaceApplicationRunConfigurationSettingsEditor: SettingsEditor<SpaceApplicationRunConfiguration>() {

    companion object {
        private val tunnelingToolKey = Key<String>("tunneling-tool")
    }

    private lateinit var panel: JPanel
    private lateinit var tunnelingToolComboBox: JComboBox<Any>

    override fun resetEditorFrom(s: SpaceApplicationRunConfiguration) {
        val tunnelingTool = s.getUserData(tunnelingToolKey) ?: tunnelingToolComboBox.model.getElementAt(0)
        tunnelingToolComboBox.selectedItem = tunnelingTool

    }

    override fun applyEditorTo(s: SpaceApplicationRunConfiguration) {
        val tunnelingTool = tunnelingToolComboBox.selectedItem?.toString()
        s.putUserData(tunnelingToolKey, tunnelingTool)

    }

    override fun createEditor(): JComponent {
        tunnelingToolComboBox.model = TunnelingToolComboBoxModel()
        return panel
    }
}