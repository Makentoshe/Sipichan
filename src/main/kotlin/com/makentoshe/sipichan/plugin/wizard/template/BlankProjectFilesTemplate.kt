package com.makentoshe.sipichan.plugin.wizard.template

import com.intellij.ide.fileTemplates.FileTemplate

/** Object provides several file templates for the blank project building */
object BlankProjectFilesTemplate: FilesTemplate() {

    /** src/Application.kt file template */
    val ApplicationKtTemplate: FileTemplate
        get() = getOrCreateTemplate("Application") { name ->
            templateManager.addTemplate(name, "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/empty/Application.kt.ft")
                template.text = String(stream!!.readBytes())
            }
        }
}