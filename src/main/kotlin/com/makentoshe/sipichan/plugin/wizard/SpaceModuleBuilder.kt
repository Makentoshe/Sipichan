package com.makentoshe.sipichan.plugin.wizard

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.makentoshe.sipichan.plugin.wizard.step.FirstModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.step.SecondModuleWizardStep
import java.io.File
import java.io.IOException
import java.nio.file.Files


class SpaceModuleBuilder : ModuleBuilder() {

    override fun getModuleType(): ModuleType<*> {
        return SpaceModuleType.getInstance()
    }

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel) {
        // Check project location is defined
        val contentEntryPath = this.contentEntryPath
        if (contentEntryPath == null || StringUtil.isEmpty(contentEntryPath)) return

        // get project directory in local file system and set to ModifiableRootModel
        val contentRootDirectory = File(contentEntryPath)
        FileUtilRt.createDirectory(contentRootDirectory)
        val localFileSystem = LocalFileSystem.getInstance()
        val virtualRootDirectory = localFileSystem.refreshAndFindFileByIoFile(contentRootDirectory) ?: return
        modifiableRootModel.addContentEntry(virtualRootDirectory)

        // receive project and module
        val project = modifiableRootModel.project
        val module = modifiableRootModel.module
        println(project)
        println(module)
        setupTestFile(virtualRootDirectory)
    }

    override fun createWizardSteps(
        wizardContext: WizardContext,
        modulesProvider: ModulesProvider
    ): Array<ModuleWizardStep> {
        return arrayOf(FirstModuleWizardStep(wizardContext, modulesProvider), SecondModuleWizardStep())
    }

    override fun getCustomOptionsStep(context: WizardContext, parentDisposable: Disposable): ModuleWizardStep {
        return InitialSpaceModuleWizardStep(context, parentDisposable)
    }

    private fun setupTestFile(projectRoot: VirtualFile) {
        val title = "build.gradle"
        val file = try {
            createVirtualFile(projectRoot, "gradle.build.sas")
        } catch (exception: IOException) {
            throw ConfigurationException(exception.message)
        }

        println(file)
    }

    private fun createVirtualFile(parent: VirtualFile, title: String): VirtualFile {
        val file = parent.toNioPath().resolve(title)

        Files.createDirectories(file.parent)
        Files.createFile(file)

        val virtualFile = VfsUtil.findFile(file, true)
        // Todo specify message
            ?: throw ConfigurationException("VirtualFile($parent/$title) is null")
        if (virtualFile.isDirectory) {
            // Todo specify message
            throw ConfigurationException("VirtualFile($parent/$title) is directory")
        }

        VfsUtil.markDirtyAndRefresh(false, false, false, virtualFile)
        return virtualFile
    }

}

