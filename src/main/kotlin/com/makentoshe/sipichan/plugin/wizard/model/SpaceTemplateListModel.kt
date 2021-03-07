package com.makentoshe.sipichan.plugin.wizard.model

import com.makentoshe.sipichan.plugin.StringsBundle
import javax.swing.AbstractListModel

class SpaceTemplateListModel : AbstractListModel<String>() {

    override fun getElementAt(index: Int): String = when (index) {
        0 -> StringsBundle.string("space.wizard.template.blank.title")
        1 -> StringsBundle.string("space.wizard.template.chatbot.title")
        2 -> StringsBundle.string("space.wizard.template.client.title")
        else -> throw IllegalArgumentException()
    }

    override fun getSize() = 3
}