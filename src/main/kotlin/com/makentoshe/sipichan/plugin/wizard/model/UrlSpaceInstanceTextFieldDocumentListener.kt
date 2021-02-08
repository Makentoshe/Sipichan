package com.makentoshe.sipichan.plugin.wizard.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class UrlSpaceInstanceTextFieldDocumentListener(
    private val textField: JTextField,
    private val client: OkHttpClient,
    private val scope: CoroutineScope
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
            scope.launch(Dispatchers.IO) {
                val request = Request.Builder().head().url(url).build()
                val response = client.newCall(request).execute()
                println(response)
            }
        }
    }
}