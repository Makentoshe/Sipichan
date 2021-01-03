package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.module.Module
import com.makentoshe.sipichan.plugin.wizard.strategy.SpaceModuleBuilderProperties

class EmptyProjectSourceProvider(private val properties: SpaceModuleBuilderProperties) : ProjectSourceProvider {

    override fun buildProjectSourceFiles(module: Module) {
        //TODO create resources and templates for empty project

    }
}