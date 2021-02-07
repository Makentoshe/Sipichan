package com.makentoshe.sipichan.plugin.wizard

import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.module.ModuleTypeManager
import com.makentoshe.sipichan.plugin.IconProvider
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import javax.swing.Icon

class SpaceModuleType : ModuleType<SpaceModuleBuilder>(id) {

    companion object {
        // TODO(refactor) Specify module id
        private const val id = "SIPICHAN_MODULE"

        fun getInstance() = ModuleTypeManager.getInstance().findByID(id) as SpaceModuleType
    }

    override fun createModuleBuilder(): SpaceModuleBuilder {
        val client = HttpClient(CIO)
        return SpaceModuleBuilder(SpaceWizard(), client)
    }

    override fun getName(): String {
        return "Space Application"
    }

    // TODO(refactor) Specify proper description
    override fun getDescription(): String {
        return "Example custom module type"
    }

    // TODO(fix) Fix logo icon
    override fun getNodeIcon(isOpened: Boolean): Icon {
        return IconProvider.logo
    }

}