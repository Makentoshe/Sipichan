package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.ClientCredentialsFlow
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import com.makentoshe.sipichan.plugin.wizard.VerificationTokenEndpoint
import com.makentoshe.sipichan.plugin.wizard.model.UrlSpaceInstanceController
import com.makentoshe.sipichan.plugin.wizard.model.UrlSpaceInstanceStatus
import com.makentoshe.sipichan.plugin.wizard.model.UrlSpaceInstanceTextFieldDocumentListener
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import java.awt.Desktop
import java.net.MalformedURLException
import java.net.URL
import javax.swing.*

// TODO step for defining space constants (client id, client secret, verification token, etc)

// Page for creating applications - https://makentoshe.jetbrains.space/manage/applications
class SpaceModuleWizardStep(
    private val wizard: SpaceWizard,
    private val client: OkHttpClient,
    private val wizardCoroutineScope: CoroutineScope
) : ModuleWizardStep(), SpaceModuleWizardStepPresenter {

    private val urlSpaceInstanceController = UrlSpaceInstanceController(wizardCoroutineScope, client)

    private lateinit var panel: JPanel
    private lateinit var urlSpaceInstanceTextField: JTextField
    private lateinit var networkCheckProgressBar: JProgressBar
    private lateinit var descriptionPanel: JPanel
    private lateinit var descriptionLabel: JLabel
    private lateinit var contentPanel: JPanel
    private lateinit var gotoCreateNewSpaceApplicationButton: JButton

    private lateinit var credentialsSelectPanel: JPanel
    private val credentialsButtonGroup = ButtonGroup()
    private lateinit var clientCredentialsFlowRadioButton: JRadioButton

    private lateinit var clientCredentialsPanel: JPanel
    private lateinit var clientIdTextField: JTextField
    private lateinit var clientSecretTextField: JTextField

    private lateinit var endpointSelectPanel: JPanel
    private val endpointButtonGroup = ButtonGroup()
    private lateinit var verificationTokenEndpointRadioButton: JRadioButton

    private lateinit var endpointVerificationPanel: JPanel
    private lateinit var verificationTokenTextField: JTextField

    init {
        networkCheckProgressBar.isIndeterminate = true
        hideNetworkCheckProgressBar()
        hideDescription()
        hideContent()

        credentialsButtonGroup.add(clientCredentialsFlowRadioButton)
        endpointButtonGroup.add(verificationTokenEndpointRadioButton)

        gotoCreateNewSpaceApplicationButton.addActionListener {
            val url = getCurrentSpaceInstanceUrl() ?: return@addActionListener
            Desktop.getDesktop().browse(URL(url.protocol, url.host, "/manage/applications").toURI())
        }

        urlSpaceInstanceTextField.document.addDocumentListener(
            UrlSpaceInstanceTextFieldDocumentListener(
                this, urlSpaceInstanceController, ::onUrlSpaceInstanceStatusChange
            )
        )
    }

    override fun getComponent() = panel

    // TODO add normal descriptions
    private fun onUrlSpaceInstanceStatusChange(status: UrlSpaceInstanceStatus) = when (status) {
        UrlSpaceInstanceStatus.Start -> {
            showNetworkCheckProgressBar()
        }
        is UrlSpaceInstanceStatus.Success -> {
            hideNetworkCheckProgressBar()
            showDescription("Success")
            showContent()
        }
        is UrlSpaceInstanceStatus.Failure -> {
            showDescription(status.exception.localizedMessage)
            hideNetworkCheckProgressBar()
            hideContent()
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

    override fun getCurrentSpaceInstanceString(): String {
        return urlSpaceInstanceTextField.text
    }

    override fun getCurrentSpaceInstanceUrl() = try {
        URL(getCurrentSpaceInstanceString())
    } catch (mue: MalformedURLException) {
        try {
            URL("https://${getCurrentSpaceInstanceString()}")
        } catch (e: MalformedURLException) {
            null
        }
    }

    override fun showContent() {
        contentPanel.isVisible = true
    }

    override fun hideContent() {
        contentPanel.isVisible = false
    }

    override fun updateDataModel() {
        if (clientCredentialsFlowRadioButton.isSelected) {
            updateDataModelClientCredentialsFlow()
        }
        if (verificationTokenEndpointRadioButton.isSelected) {
            updateDataModelVerificationTokenEndpoint()
        }
    }

    private fun updateDataModelClientCredentialsFlow() {
        val clientId = clientIdTextField.text
        val clientSecret = clientSecretTextField.text
        wizard.clientCredentialsFlow = ClientCredentialsFlow(clientId, clientSecret)
    }

    private fun updateDataModelVerificationTokenEndpoint() {
        val verificationToken = verificationTokenTextField.text
        if (verificationToken.isBlank()) return
        wizard.verificationTokenEndpoint = VerificationTokenEndpoint(verificationToken)
    }
}
