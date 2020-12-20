package com.makentoshe.sipichan.plugin

import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.module.ModuleTypeManager
import javax.swing.Icon

class DemoModuleType: ModuleType<DemoModuleBuilder>(id) {

    companion object {
        private const val id = "DEMO_MODULE_TYPE"

        fun getInstance() = ModuleTypeManager.getInstance().findByID(id) as DemoModuleType
    }

    override fun createModuleBuilder(): DemoModuleBuilder {
        return DemoModuleBuilder()
    }

    override fun getName(): String {
        return "Space Application"
    }

    override fun getDescription(): String {
        return "Example custom module type"
    }

    override fun getNodeIcon(isOpened: Boolean): Icon {
        return IconProvider.logo
    }

}