package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
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

    init {
        networkCheckProgressBar.isIndeterminate = true
        hideNetworkCheckProgressBar()
        hideDescription()
        hideContent()

        gotoCreateNewSpaceApplicationButton.addActionListener {
            val url = getCurrentSpaceInstanceUrl() ?: return@addActionListener
            val u = URL(url.protocol, url.host, "/manage/applications")
            println(u)
            Desktop.getDesktop().browse(u.toURI())
        }

        urlSpaceInstanceTextField.document.addDocumentListener(
            UrlSpaceInstanceTextFieldDocumentListener(
                this, urlSpaceInstanceController, ::onUrlSpaceInstanceStatusChange
            )
        )
    }

    override fun getComponent(): JComponent {
        println("Panel")
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

    // called on next step
    override fun updateDataModel() {
        println("Update")
    }
}
