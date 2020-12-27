package com.makentoshe.sipichan.plugin.wizard.strategy

import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.vfs.VirtualFile

interface SpaceModuleBuilderStrategy {

    fun setupRootModel(modifiableRootModel: ModifiableRootModel, virtualRootDirectory: VirtualFile)

    fun setupModule(module: Module)
}