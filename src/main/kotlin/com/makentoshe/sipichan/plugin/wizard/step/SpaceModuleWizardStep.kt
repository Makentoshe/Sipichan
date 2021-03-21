package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.makentoshe.sipichan.plugin.domain
import com.makentoshe.sipichan.plugin.wizard.*
import com.makentoshe.sipichan.plugin.wizard.model.UrlSpaceInstanceController
import com.makentoshe.sipichan.plugin.wizard.model.UrlSpaceInstanceStatus
import com.makentoshe.sipichan.plugin.wizard.model.UrlSpaceInstanceTextFieldDocumentListener
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import java.awt.Desktop
import java.awt.event.ItemEvent.SELECTED
import java.net.MalformedURLException
import java.net.URL
import javax.swing.*

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
    private lateinit var endpointVerificationTokenPanel: JPanel
    private lateinit var endpointSigningKeyPanel: JPanel

    private lateinit var verificationTokenEndpointCheckBox: JCheckBox
    private lateinit var verificationTokenEndpointTextField: JTextField

    private lateinit var signingKeyEndpointCheckBox: JCheckBox
    private lateinit var signingKeyEndpointTextField: JTextField

    init {
        networkCheckProgressBar.isIndeterminate = true
        hideNetworkCheckProgressBar()
        hideDescription()
        hideContent()

        credentialsButtonGroup.add(clientCredentialsFlowRadioButton)

        gotoCreateNewSpaceApplicationButton.addActionListener {
            val url = getCurrentSpaceInstanceUrl() ?: return@addActionListener
            Desktop.getDesktop().browse(URL(url.protocol, url.host, "/manage/applications").toURI())
        }

        urlSpaceInstanceTextField.document.addDocumentListener(
            UrlSpaceInstanceTextFieldDocumentListener(
                this, urlSpaceInstanceController, ::onUrlSpaceInstanceStatusChange
            )
        )

        verificationTokenEndpointCheckBox.addItemListener {
            endpointVerificationTokenPanel.isVisible = it.stateChange == SELECTED
        }

        signingKeyEndpointCheckBox.addItemListener {
            endpointSigningKeyPanel.isVisible = it.stateChange == SELECTED
        }
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
        if (verificationTokenEndpointCheckBox.isSelected) {
            updateDataModelVerificationTokenEndpoint()
        }

        val url = getCurrentSpaceInstanceUrl() ?: return
        wizard.spaceInstance = SpaceInstanceUrl(url.domain().toString())
    }

    private fun updateDataModelClientCredentialsFlow() {
        val clientId = clientIdTextField.text
        val clientSecret = clientSecretTextField.text
        wizard.clientCredentialsFlow = ClientCredentialsFlow(clientId, clientSecret)
    }

    private fun updateDataModelVerificationTokenEndpoint() {
        val verificationToken = if (endpointVerificationTokenPanel.isVisible) verificationTokenEndpointTextField.text else null
        val signingKey = if(endpointSigningKeyPanel.isVisible) signingKeyEndpointTextField.text else null
        wizard.endpoints = SpaceEndpoints(verificationToken, signingKey)
    }
}
