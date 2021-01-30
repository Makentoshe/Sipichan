package com.makentoshe.sipichan.plugin.configuration

import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.util.Key
import com.makentoshe.sipichan.plugin.configuration.model.TunnelingToolComboBoxModel
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class SpaceApplicationRunConfigurationSettingsEditor: SettingsEditor<SpaceApplicationRunConfiguration>() {

    companion object {
        private val tunnelingToolKey = Key<String>("tunneling-tool")
        private val tunnelingToolPathKey = Key<String>("tunneling-tool-path")
    }

    private lateinit var panel: JPanel
    private lateinit var tunnelingToolComboBox: JComboBox<Any>
    // TODO add tooltip for any selectable tool
    // localtunnel - path to npx
    private lateinit var tunnelingToolPathTextField: JTextField

    override fun resetEditorFrom(s: SpaceApplicationRunConfiguration) {
        val tunnelingTool = s.getUserData(tunnelingToolKey) ?: tunnelingToolComboBox.model.getElementAt(0)
        tunnelingToolComboBox.selectedItem = tunnelingTool

        val tunnelingToolPath = s.getUserData(tunnelingToolPathKey) ?: ""
        tunnelingToolPathTextField.text = tunnelingToolPath
    }

    override fun applyEditorTo(s: SpaceApplicationRunConfiguration) {
        val tunnelingTool = tunnelingToolComboBox.selectedItem?.toString()
        s.putUserData(tunnelingToolKey, tunnelingTool)

        val tunnelingToolPath = tunnelingToolPathTextField.text
        s.putUserData(tunnelingToolPathKey, tunnelingToolPath)
    }

    override fun createEditor(): JComponent {
        tunnelingToolComboBox.model = TunnelingToolComboBoxModel()
        return panel
    }
}