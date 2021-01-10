package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.makentoshe.sipichan.plugin.wizard.strategy.SpaceModuleBuilderProperties
import com.makentoshe.sipichan.plugin.wizard.template.BlankProjectFilesTemplate
import com.makentoshe.sipichan.plugin.wizard.template.SpaceFilesTemplate
import java.io.File

class BlankProjectSourceProvider(private val properties: SpaceModuleBuilderProperties) : ProjectSourceProvider() {

    override fun buildProjectSourceFiles(modifiableRootModel: ModifiableRootModel, virtualRootDirectory: VirtualFile) {
        val srcDirectory = File(virtualRootDirectory.path, "src")
        FileUtil.createDirectory(srcDirectory)
        createApplicationKtFile(srcDirectory)

        val resourcesDirectory = File(virtualRootDirectory.path, "resources")
        FileUtil.createDirectory(resourcesDirectory)
        createLogbackXmlFile(resourcesDirectory)
        createApplicationConfFile(resourcesDirectory)
    }

    private fun createApplicationKtFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "Application.kt")
        val applicationContent = BlankProjectFilesTemplate.ApplicationKtTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createLogbackXmlFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "logback.xml")
        val applicationContent = SpaceFilesTemplate.LogbackXmlTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createApplicationConfFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "application.conf")
        val applicationContent = SpaceFilesTemplate.ApplicationConfTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }
}