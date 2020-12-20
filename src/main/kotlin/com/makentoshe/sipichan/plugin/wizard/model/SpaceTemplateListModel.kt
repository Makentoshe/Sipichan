package com.makentoshe.sipichan.plugin.wizard.model

import com.makentoshe.sipichan.plugin.StringsBundle
import javax.swing.AbstractListModel

class SpaceTemplateListModel : AbstractListModel<String>() {

    override fun getElementAt(index: Int): String = when (index) {
        0 -> StringsBundle.string("space.wizard.template.empty.title")
        1 -> StringsBundle.string("space.wizard.template.chatbot.title")
        2 -> StringsBundle.string("space.wizard.template.slashcommand.title")
        else -> throw IllegalArgumentException()
    }

    override fun getSize() = 3
}