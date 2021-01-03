package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.makentoshe.sipichan.plugin.wizard.SpaceFileTemplate
import com.makentoshe.sipichan.plugin.wizard.strategy.SpaceModuleBuilderProperties
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class EmptyProjectSourceProvider(private val properties: SpaceModuleBuilderProperties) : ProjectSourceProvider {

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
        val applicationContent = SpaceFileTemplate.MainApplicationKtTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createLogbackXmlFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "logback.xml")
        val applicationContent = SpaceFileTemplate.MainLogbackXmlTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createApplicationConfFile(parent: File) {
        val applicationFile = createVirtualFile(parent.path, "application.conf")
        val applicationContent = SpaceFileTemplate.MainApplicationConfTemplate.getText(properties.attributes())
        VfsUtil.saveText(applicationFile, applicationContent)
    }

    private fun createVirtualFile(parent: String, title: String): VirtualFile = try {
        val file = createFile(Paths.get(parent), title)

        // Todo specify message
        val virtualFile = VfsUtil.findFile(file, true)
            ?: throw ConfigurationException("VirtualFile($parent/$title) is null")

        // Todo specify message
        if (virtualFile.isDirectory) {
            throw ConfigurationException("VirtualFile($parent/$title) is directory")
        }

        VfsUtil.markDirtyAndRefresh(false, false, false, virtualFile)
        virtualFile
    } catch (exception: IOException) {
        throw ConfigurationException(exception.message)
    }

    private fun createFile(parent: Path, title: String) = parent.resolve(title).also { file ->
        Files.deleteIfExists(file)
        Files.createDirectories(parent)
        Files.createFile(file)
    }
}