package com.makentoshe.sipichan.plugin.wizard.source

import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

abstract class ProjectSourceProvider {

    abstract fun buildProjectSourceFiles(modifiableRootModel: ModifiableRootModel, virtualRootDirectory: VirtualFile)

    protected fun createVirtualFile(parent: String, title: String): VirtualFile = try {
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
