package com.makentoshe.sipichan.plugin.wizard.model

import com.makentoshe.sipichan.plugin.wizard.step.SpaceModuleWizardStepPresenter
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class UrlSpaceInstanceTextFieldDocumentListener(
    private val presenter: SpaceModuleWizardStepPresenter,
    private val controller: UrlSpaceInstanceController,
    private val listener: (UrlSpaceInstanceStatus) -> Unit
) : DocumentListener {
    override fun changedUpdate(e: DocumentEvent) = Unit
    override fun insertUpdate(e: DocumentEvent) = internalChangeUpdate(e)
    override fun removeUpdate(e: DocumentEvent) = internalChangeUpdate(e)

    private val delay = 750L // after 0.75 sec after input url will be checked
    private var timer = Timer()

    private fun internalChangeUpdate(e: DocumentEvent) {
        listener.invoke(UrlSpaceInstanceStatus.Start)

        timer.cancel()
        try {
            timer = Timer().apply { schedule(task(isValidUrl(presenter.getCurrentSpaceInstanceUrl())), delay) }
        } catch (e: MalformedURLException) {
            listener.invoke(UrlSpaceInstanceStatus.Failure(presenter.getCurrentSpaceInstanceUrl(), e))
        }
    }

    private fun isValidUrl(string: String): URL = try {
        URL(string)
    } catch (e: MalformedURLException) {
        URL("https://$string")
    }

    private fun task(url: URL) = object : TimerTask() {
        override fun run() = controller.check(url, listener)
    }
}