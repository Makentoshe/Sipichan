package com.makentoshe.sipichan.plugin.wizard

import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.module.ModuleTypeManager
import com.makentoshe.sipichan.plugin.IconProvider
import com.makentoshe.sipichan.plugin.wizard.source.BlankProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.strategy.GradleSpaceModuleBuilderProperties
import com.makentoshe.sipichan.plugin.wizard.strategy.GradleSpaceModuleBuilderStrategy
import javax.swing.Icon

class SpaceModuleType: ModuleType<SpaceModuleBuilder>(id) {

    companion object {
        // TODO(refactor) Specify module id
        private const val id = "DEMO_MODULE_TYPE"

        fun getInstance() = ModuleTypeManager.getInstance().findByID(id) as SpaceModuleType
    }

    override fun createModuleBuilder(): SpaceModuleBuilder {
        // TODO add switching between build systems
        // TODO hardcoded (add properties define)
        val gradleProperties = GradleSpaceModuleBuilderProperties("com.makentoshe", "sipichan", "1.0.0")
        val sourceProvider = BlankProjectSourceProvider(gradleProperties)
        val gradleStrategy = GradleSpaceModuleBuilderStrategy(gradleProperties, sourceProvider)
        return SpaceModuleBuilder(gradleStrategy)
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