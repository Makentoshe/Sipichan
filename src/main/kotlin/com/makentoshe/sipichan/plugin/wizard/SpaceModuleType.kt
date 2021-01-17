package com.makentoshe.sipichan.plugin.wizard

import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.module.ModuleTypeManager
import com.makentoshe.sipichan.plugin.IconProvider
import javax.swing.Icon

class SpaceModuleType : ModuleType<SpaceModuleBuilder>(id) {

    companion object {
        // TODO(refactor) Specify module id
        private const val id = "DEMO_MODULE_TYPE"

        fun getInstance() = ModuleTypeManager.getInstance().findByID(id) as SpaceModuleType
    }

    override fun createModuleBuilder(): SpaceModuleBuilder {
        return SpaceModuleBuilder(SpaceWizard())
    }

    override fun getName(): String {
        return "Space Application"
    }

    // TODO(refactor) Specify proper description
    override fun getDescription(): String {
        return "Example custom module type"
    }

    override fun getNodeIcon(isOpened: Boolean): Icon {
        return IconProvider.logo
    }

}