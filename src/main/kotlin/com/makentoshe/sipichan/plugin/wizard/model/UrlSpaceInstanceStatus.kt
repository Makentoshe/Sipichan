package com.makentoshe.sipichan.plugin.wizard.model

import java.net.URL

sealed class UrlSpaceInstanceStatus {

    object Start : UrlSpaceInstanceStatus()

    data class Success(val url: URL) : UrlSpaceInstanceStatus()

    data class Failure(val url: String, val exception: Exception) : UrlSpaceInstanceStatus()

}