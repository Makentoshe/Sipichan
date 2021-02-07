package com.makentoshe.sipichan.plugin.wizard.step

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.makentoshe.sipichan.plugin.wizard.SpaceWizard
import io.ktor.client.*
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JProgressBar
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

// TODO step for defining space constants (client id, client secret, verification token, etc)
class SpaceModuleWizardStep(private val wizard: SpaceWizard, private val client: HttpClient) : ModuleWizardStep() {

    private lateinit var panel: JPanel
    private lateinit var urlSpaceInstanceTextField: JTextField
    private lateinit var networkCheckProgressBar: JProgressBar
    private lateinit var content: JPanel

    override fun getComponent(): JComponent {
        // TODO move to separate class
        val listener = UrlSpaceInstanceTextFieldDocumentListener(urlSpaceInstanceTextField, client)
        urlSpaceInstanceTextField.document.addDocumentListener(listener)

        networkCheckProgressBar.isIndeterminate = true
        content.isVisible = false
        return panel
    }

    // called on next step
    override fun updateDataModel() = Unit
}

class UrlSpaceInstanceTextFieldDocumentListener(
    private val textField: JTextField,
    private val client: HttpClient
) : DocumentListener {
    override fun changedUpdate(e: DocumentEvent) = Unit
    override fun insertUpdate(e: DocumentEvent) = internalChangeUpdate(e)
    override fun removeUpdate(e: DocumentEvent) = internalChangeUpdate(e)

    private val delay = 750L // after 0.75 sec after input url will be checked
    private var timer = Timer()

    private fun internalChangeUpdate(e: DocumentEvent) {
        timer.cancel()
        timer = Timer().apply { schedule(task(isValidUrl(textField.text) ?: return), delay) }
    }

    private fun isValidUrl(string: String): URL? = try {
        URL(string)
    } catch (e: MalformedURLException) {
        null
    }

    // TODO make head request and define Space
    private fun task(url: URL) = object : TimerTask() {
        override fun run() {
            println("Check url: $url")
        }
    }
}