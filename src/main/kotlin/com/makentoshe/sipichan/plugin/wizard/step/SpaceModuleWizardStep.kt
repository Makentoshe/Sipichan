package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import com.makentoshe.sipichan.plugin.wizard.model.CloseableCoroutineScope
import com.makentoshe.sipichan.plugin.wizard.model.UrlSpaceInstanceController
import com.makentoshe.sipichan.plugin.wizard.model.UrlSpaceInstanceStatus
import com.makentoshe.sipichan.plugin.wizard.model.UrlSpaceInstanceTextFieldDocumentListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import javax.swing.*

// TODO step for defining space constants (client id, client secret, verification token, etc)
class SpaceModuleWizardStep(
    private val wizard: SpaceWizard, private val client: OkHttpClient
) : ModuleWizardStep(), SpaceModuleWizardStepPresenter {

    private val coroutineScope = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val urlSpaceInstanceController = UrlSpaceInstanceController(coroutineScope, client)

    private lateinit var panel: JPanel
    private lateinit var urlSpaceInstanceTextField: JTextField
    private lateinit var networkCheckProgressBar: JProgressBar
    private lateinit var descriptionPanel: JPanel
    private lateinit var descriptionLabel: JLabel
    private lateinit var contentPanel: JPanel

    override fun getComponent(): JComponent {
        val listener = UrlSpaceInstanceTextFieldDocumentListener(this, urlSpaceInstanceController) {
            onUrlSpaceInstanceStatusChange(it)
        }
        urlSpaceInstanceTextField.document.addDocumentListener(listener)

        hideNetworkCheckProgressBar()
        hideDescription()
        return panel
    }

    // TODO add normal descriptions
    private fun onUrlSpaceInstanceStatusChange(status: UrlSpaceInstanceStatus) = when (status) {
        UrlSpaceInstanceStatus.Start -> {
            showNetworkCheckProgressBar()
        }
        is UrlSpaceInstanceStatus.Success -> {
            hideNetworkCheckProgressBar()
            showDescription("Success")
        }
        is UrlSpaceInstanceStatus.Failure -> {
            showDescription(status.exception.localizedMessage)
            hideNetworkCheckProgressBar()
        }
    }

    override fun showNetworkCheckProgressBar() {
        networkCheckProgressBar.isVisible = true
    }

    override fun hideNetworkCheckProgressBar() {
        networkCheckProgressBar.isVisible = false
    }

    override fun showDescription(string: String) {
        descriptionLabel.text = string
        descriptionPanel.isVisible = true
    }

    override fun hideDescription() {
        descriptionPanel.isVisible = false
    }

    override fun getCurrentSpaceInstanceUrl(): String {
        return urlSpaceInstanceTextField.text
    }

    // called on next step
    override fun updateDataModel() {
        coroutineScope.close()
    }
}
