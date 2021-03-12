package com.makentoshe.sipichan.plugin.wizard.template

import com.intellij.ide.fileTemplates.FileTemplate

object ClientProjectFilesTemplate : FilesTemplate() {

    private val prefix = "client"

    /** src/main/kotlin/.../Client.kt file template */
    val ClientKtTemplate: FileTemplate
        get() = getOrCreateTemplate("Client") { name ->
            templateManager.addTemplate("$prefix$name", "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/client/$name.kt.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    val MainKtTemplate: FileTemplate
        get() = getOrCreateTemplate("Main") { name ->
            templateManager.addTemplate("$prefix$name", "kt").also{ template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/client/$name.kt.ft")
                template.text = String(stream!!.readBytes())
            }
        }
}