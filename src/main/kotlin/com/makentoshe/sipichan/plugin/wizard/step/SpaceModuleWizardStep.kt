package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import com.makentoshe.sipichan.plugin.wizard.model.CloseableCoroutineScope
import com.makentoshe.sipichan.plugin.wizard.model.UrlSpaceInstanceTextFieldDocumentListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JProgressBar
import javax.swing.JTextField

// TODO step for defining space constants (client id, client secret, verification token, etc)
class SpaceModuleWizardStep(private val wizard: SpaceWizard, private val client: OkHttpClient) : ModuleWizardStep() {

    private val coroutineScope = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var panel: JPanel
    private lateinit var urlSpaceInstanceTextField: JTextField
    private lateinit var networkCheckProgressBar: JProgressBar
    private lateinit var content: JPanel

    override fun getComponent(): JComponent {
        // TODO move to separate class
        val listener = UrlSpaceInstanceTextFieldDocumentListener(urlSpaceInstanceTextField, client, coroutineScope)
        urlSpaceInstanceTextField.document.addDocumentListener(listener)

        networkCheckProgressBar.isIndeterminate = true
        content.isVisible = false
        return panel
    }

    // called on next step
    override fun updateDataModel() {
        coroutineScope.close()
    }
}
