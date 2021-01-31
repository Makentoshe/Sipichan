package com.makentoshe.sipichan.plugin.configuration

import com.intellij.openapi.options.SettingsEditor
import com.makentoshe.sipichan.plugin.configuration.SpaceApplicationRunConfiguration.Companion.tunnelingToolKey
import com.makentoshe.sipichan.plugin.configuration.SpaceApplicationRunConfiguration.Companion.tunnelingToolPathKey
import com.makentoshe.sipichan.plugin.configuration.model.TunnelingTool
import com.makentoshe.sipichan.plugin.configuration.model.TunnelingToolComboBoxModel
import javax.swing.*

class SpaceApplicationRunConfigurationSettingsEditor: SettingsEditor<SpaceApplicationRunConfiguration>() {

    private lateinit var panel: JPanel
    private lateinit var tunnelingToolComboBox: JComboBox<TunnelingTool>
    // TODO add tooltip for any selectable tool
    // localtunnel - path to npx
    // ngrok - path to ngrok.exe or something same
    private lateinit var tunnelingToolPathTextField: JTextField

    // TODO replace hardcoded values
    override fun resetEditorFrom(s: SpaceApplicationRunConfiguration) {
        val tunnelingTool = s.getUserData(tunnelingToolKey) ?: tunnelingToolComboBox.model.getElementAt(0)
        tunnelingToolComboBox.selectedItem = tunnelingTool

        val tunnelingToolPath = s.getUserData(tunnelingToolPathKey) ?: "P:\\custom\\nodejs"
        tunnelingToolPathTextField.text = tunnelingToolPath
    }

    override fun applyEditorTo(s: SpaceApplicationRunConfiguration) {
        val tunnelingTool = tunnelingToolComboBox.selectedItem as TunnelingTool
        s.putUserData(tunnelingToolKey, tunnelingTool)

        val tunnelingToolPath = tunnelingToolPathTextField.text
        s.putUserData(tunnelingToolPathKey, tunnelingToolPath)
    }

    override fun createEditor(): JComponent {
        tunnelingToolComboBox.model = TunnelingToolComboBoxModel() as ComboBoxModel<TunnelingTool>
        return panel
    }
}