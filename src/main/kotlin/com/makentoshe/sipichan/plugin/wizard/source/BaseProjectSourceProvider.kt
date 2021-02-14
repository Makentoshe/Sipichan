package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.makentoshe.sipichan.plugin.wizard.BuildConfiguration
import com.makentoshe.sipichan.plugin.wizard.template.SpaceFilesTemplate
import java.io.File

abstract class BaseProjectSourceProvider(private val buildConfiguration: BuildConfiguration): ProjectSourceProvider() {

    override fun buildProjectSourceFiles(modifiableRootModel: ModifiableRootModel, virtualRootDirectory: VirtualFile) {
        val resourcesDirectory = File(virtualRootDirectory.path, "resources")
        FileUtil.createDirectory(resourcesDirectory)
        createLogbackXmlFile(resourcesDirectory)
        createApplicationConfFile(resourcesDirectory)

        val srcDirectory = File(virtualRootDirectory.path, "src")
        FileUtil.createDirectory(srcDirectory)
        buildSrcFiles(srcDirectory)
    }

    abstract fun buildSrcFiles(srcDirectoryRoot: File)

    private fun createLogbackXmlFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "logback.xml")
        val applicationContent = SpaceFilesTemplate.LogbackXmlTemplate.getText(buildConfiguration.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createApplicationConfFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "application.conf")
        val applicationContent = SpaceFilesTemplate.ApplicationConfTemplate.getText(buildConfiguration.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }
}