package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import com.makentoshe.sipichan.plugin.wizard.template.ClientProjectFilesTemplate
import java.io.File

class ClientProjectSourceProvider(
    private val spaceWizard: SpaceWizard
) : ProjectSourceProvider() {

    override fun buildProjectSourceFiles(modifiableRootModel: ModifiableRootModel, virtualRootDirectory: VirtualFile) {
        val srcDirectoryRoot = File(virtualRootDirectory.path, "src/main/kotlin")

        createClientKtFile(srcDirectoryRoot)
        createMainKtFile(srcDirectoryRoot)
    }

    private fun createMainKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Main.kt")
        val applicationContent = ClientProjectFilesTemplate.MainKtTemplate.getText(spaceWizard.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createClientKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Client.kt")
        val applicationContent = ClientProjectFilesTemplate.ClientKtTemplate.getText(spaceWizard.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

}