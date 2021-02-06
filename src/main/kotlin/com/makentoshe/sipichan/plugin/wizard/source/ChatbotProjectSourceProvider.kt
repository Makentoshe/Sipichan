package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.vfs.VfsUtil
import com.makentoshe.sipichan.plugin.wizard.strategy.SpaceModuleBuilderProperties
import com.makentoshe.sipichan.plugin.wizard.template.ChatbotProjectFilesTemplate
import java.io.File

class ChatbotProjectSourceProvider(
    private val properties: SpaceModuleBuilderProperties
): BaseProjectSourceProvider(properties) {

    override fun buildSrcFiles(srcDirectoryRoot: File) {
        createApplicationKtFile(srcDirectoryRoot)
        createCallContextKtFile(srcDirectoryRoot)
        createClientKtFile(srcDirectoryRoot)
        createCommandsKtFile(srcDirectoryRoot)
    }

    private fun createApplicationKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Application.kt")
        val applicationContent = ChatbotProjectFilesTemplate.ApplicationKtTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createCallContextKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "CallContext.kt")
        val applicationContent = ChatbotProjectFilesTemplate.EchoContextKtTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createClientKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Client.kt")
        val applicationContent = ChatbotProjectFilesTemplate.ClientKtTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createCommandsKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Commands.kt")
        val applicationContent = ChatbotProjectFilesTemplate.CommandsKtTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }
}