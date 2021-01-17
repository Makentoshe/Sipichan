package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.vfs.VfsUtil
import com.makentoshe.sipichan.plugin.wizard.strategy.SpaceModuleBuilderProperties
import com.makentoshe.sipichan.plugin.wizard.template.BlankProjectFilesTemplate
import java.io.File

class BlankProjectSourceProvider(
    private val properties: SpaceModuleBuilderProperties
) : BaseProjectSourceProvider(properties) {

    override fun buildSrcFiles(srcDirectoryRoot: File) {
        createApplicationKtFile(srcDirectoryRoot)
    }

    private fun createApplicationKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Application.kt")
        val applicationContent = BlankProjectFilesTemplate.ApplicationKtTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }
}