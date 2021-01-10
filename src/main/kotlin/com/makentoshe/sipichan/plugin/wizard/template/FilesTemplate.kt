package com.makentoshe.sipichan.plugin.wizard.template

import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

abstract class FilesTemplate {

    protected val templateManager: FileTemplateManager = FileTemplateManager.getDefaultInstance()

    protected fun getOrCreateTemplate(name: String, create: (String) -> FileTemplate): FileTemplate {
        return templateManager.getTemplate(name) ?: create(name)
    }

    class Factory(private val template: FileTemplate, private val attributes: Map<String, String>) {

        fun create(title: String, projectRoot: VirtualFile): VirtualFile {
            val file = try {
                createVirtualFile(projectRoot, title)
            } catch (exception: IOException) {
                throw ConfigurationException(exception.message)
            }
            VfsUtil.saveText(file, template.getText(attributes))
            return file
        }

        private fun createVirtualFile(parent: VirtualFile, title: String): VirtualFile {
            val file = createFile(parent.toNioPath(), title)

            // Todo specify message
            val virtualFile = VfsUtil.findFile(file, true)
                ?: throw ConfigurationException("VirtualFile($parent/$title) is null")

            // Todo specify message
            if (virtualFile.isDirectory) {
                throw ConfigurationException("VirtualFile($parent/$title) is directory")
            }

            VfsUtil.markDirtyAndRefresh(false, false, false, virtualFile)
            return virtualFile
        }

        private fun createFile(parent: Path, title: String) = parent.resolve(title).also { file ->
            Files.deleteIfExists(file)
            Files.createDirectories(parent)
            Files.createFile(file)
        }
    }
}
