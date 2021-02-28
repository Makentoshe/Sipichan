package com.makentoshe.sipichan.plugin.wizard

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.makentoshe.sipichan.plugin.wizard.model.CloseableCoroutineScope
import com.makentoshe.sipichan.plugin.wizard.step.BuildModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.step.InitialSpaceModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.step.SpaceModuleWizardStep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import java.io.File

class SpaceModuleBuilder(private val wizardBuilder: SpaceWizard, private val client: OkHttpClient) : ModuleBuilder() {

    private var wizardCoroutineScope = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val strategy by lazy { wizardBuilder.strategy() }

    override fun getModuleType(): ModuleType<*> {
        return SpaceModuleType.getInstance()
    }

    override fun createWizardSteps(
        wizardContext: WizardContext,
        modulesProvider: ModulesProvider
    ): Array<ModuleWizardStep> {
        return arrayOf(
            SpaceModuleWizardStep(wizardBuilder, client, wizardCoroutineScope),
            BuildModuleWizardStep(wizardBuilder)
        )
    }

    override fun getCustomOptionsStep(context: WizardContext, parentDisposable: Disposable): ModuleWizardStep {
        return InitialSpaceModuleWizardStep(context, parentDisposable, wizardBuilder)
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

        strategy.setupRootModel(modifiableRootModel, virtualRootDirectory)
        wizardCoroutineScope.close()
    }

    override fun setupModule(module: Module) {
        super.setupModule(module)
        strategy.setupModule(module)
    }
}
