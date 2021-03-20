package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.vfs.VfsUtil
import com.makentoshe.sipichan.plugin.wizard.SpaceEndpoints
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import com.makentoshe.sipichan.plugin.wizard.template.ChatbotProjectFilesTemplate
import com.makentoshe.sipichan.plugin.wizard.template.common.EndpointFilesTemplate
import java.io.File

class ChatbotProjectSourceProvider(
    private val spaceWizard: SpaceWizard
) : BaseProjectSourceProvider(spaceWizard.buildConfiguration) {

    override fun buildSrcFiles(srcDirectoryRoot: File) {
        createApplicationKtFile(srcDirectoryRoot)
        createCommandsKtFile(srcDirectoryRoot)
        createEndpointKtFile(srcDirectoryRoot)
        createContextKtFile(srcDirectoryRoot)
        createClientKtFile(srcDirectoryRoot)
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

    private fun createEndpointKtFile(parent: File) {
        val endpoints = spaceWizard.endpoints ?: return
        val attributes = HashMap(spaceWizard.buildConfiguration.attributes())
        attributes.putAll(endpoints.attributes())

        attributes[SpaceEndpoints.VERIFICATION_TOKEN_PLACEHOLDER] = if (endpoints.verificationToken != null) {
            EndpointFilesTemplate.EndpointVerificationTokenTemplate.getText(attributes)
        } else ""

        attributes[SpaceEndpoints.SIGNING_KEY_PLACEHOLDER] = if(endpoints.signingKey != null) {
            EndpointFilesTemplate.EndpointSigningKeyTemplate.getText(attributes)
        } else ""

        val endpointContent = if (endpoints.verificationToken != null || endpoints.signingKey != null) {
            EndpointFilesTemplate.EndpointKtTemplate.getText(attributes)
        } else return

        val endpointFile = createVirtualFile(parent.path, "Endpoint.kt")
        VfsUtil.saveText(endpointFile, endpointContent)
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