package com.makentoshe.sipichan.plugin

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.PropertyKey

object StringsBundle : DynamicBundle("values.strings") {
    @Nls
    fun string(
        @NotNull @PropertyKey(resourceBundle = "values.strings") key: String,
        vararg params: @NotNull Any?
    ): String = getMessage(key, params)
}