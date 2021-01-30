package com.makentoshe.sipichan.plugin.configuration.model

import org.jdesktop.swingx.combobox.ListComboBoxModel

class TunnelingToolComboBoxModel: ListComboBoxModel<String>(listOf(
    "localtunnel", "ngrok"
))