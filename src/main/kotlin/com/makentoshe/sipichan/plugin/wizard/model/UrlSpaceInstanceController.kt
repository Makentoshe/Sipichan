package com.makentoshe.sipichan.plugin.wizard.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class UrlSpaceInstanceController(
    private val scope: CoroutineScope,
    private val client: OkHttpClient
) {

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun check(url: URL): UrlSpaceInstanceStatus = try {
        val request = Request.Builder().url(URL(url, "/settings.json")).build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            UrlSpaceInstanceStatus.Success(url)
        } else {
            UrlSpaceInstanceStatus.Failure(url.toString(), Exception())
        }.also { response.close() }
    } catch (e: Exception) {
        UrlSpaceInstanceStatus.Failure(url.toString(), e)
    }

    fun check(url: URL, listener: (UrlSpaceInstanceStatus) -> Unit) {
        scope.launch(Dispatchers.IO) { listener(check(url)) }
    }
}