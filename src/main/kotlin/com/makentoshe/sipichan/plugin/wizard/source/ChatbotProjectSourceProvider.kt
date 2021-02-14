package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.vfs.VfsUtil
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import com.makentoshe.sipichan.plugin.wizard.template.ChatbotProjectFilesTemplate
import java.io.File

class ChatbotProjectSourceProvider(
    private val spaceWizard: SpaceWizard
): BaseProjectSourceProvider(spaceWizard.buildConfiguration) {

    override fun buildSrcFiles(srcDirectoryRoot: File) {
        println(spaceWizard.attributes())

        createApplicationKtFile(srcDirectoryRoot)
        createCallContextKtFile(srcDirectoryRoot)
        createClientKtFile(srcDirectoryRoot)
        createCommandsKtFile(srcDirectoryRoot)
    }

    private fun createApplicationKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Application.kt")
        val applicationContent = ChatbotProjectFilesTemplate.ApplicationKtTemplate.getText(spaceWizard.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createCallContextKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "CallContext.kt")
        val applicationContent = ChatbotProjectFilesTemplate.EchoContextKtTemplate.getText(spaceWizard.attributes())
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