package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.vfs.VfsUtil
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import com.makentoshe.sipichan.plugin.wizard.template.ChatbotProjectFilesTemplate
import java.io.File

class ChatbotProjectSourceProvider(
    private val spaceWizard: SpaceWizard
): BaseProjectSourceProvider(spaceWizard.buildConfiguration) {

    override fun buildSrcFiles(srcDirectoryRoot: File) {
        createApplicationKtFile(srcDirectoryRoot)
        createContextKtFile(srcDirectoryRoot)
        createClientKtFile(srcDirectoryRoot)
        createCommandsKtFile(srcDirectoryRoot)
    }

    private fun createApplicationKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Application.kt")
        val applicationContent = ChatbotProjectFilesTemplate.ApplicationKtTemplate.getText(spaceWizard.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createContextKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Context.kt")
        val applicationContent = ChatbotProjectFilesTemplate.ContextKtTemplate.getText(spaceWizard.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createClientKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Client.kt")
        val applicationContent = ChatbotProjectFilesTemplate.ClientKtTemplate.getText(spaceWizard.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createCommandsKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Commands.kt")
        val applicationContent = ChatbotProjectFilesTemplate.CommandsKtTemplate.getText(spaceWizard.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }
}