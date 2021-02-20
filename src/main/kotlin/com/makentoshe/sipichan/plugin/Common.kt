package com.makentoshe.sipichan.plugin

import java.net.URL

/** Cuts off the url's path */
fun URL.domain(): URL {
    return URL(protocol, host, "")
}