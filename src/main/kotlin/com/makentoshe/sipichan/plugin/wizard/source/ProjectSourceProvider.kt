package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.module.Module

interface ProjectSourceProvider {
    fun buildProjectSourceFiles(module: Module)
}
