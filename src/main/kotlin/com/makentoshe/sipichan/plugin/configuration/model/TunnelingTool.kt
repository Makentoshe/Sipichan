package com.makentoshe.sipichan.plugin.configuration.model

// TODO make enum
sealed class TunnelingTool {

    abstract val title: String

    object Localtunnel : TunnelingTool() {
        override val title: String = "localtunnel"
    }

    override fun toString(): String {
        return title
    }

    companion object {
        fun getToolByTitle(title: String) = when(title) {
            "localtunnel" -> Localtunnel
            else -> null
        }
    }
}