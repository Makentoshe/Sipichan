package com.makentoshe.sipichan.plugin.wizard

import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateManager

/** Object provides several file templates for project building */
object SpaceFileTemplate {

    private val templateManager = FileTemplateManager.getDefaultInstance()

    /** build.gradle file template */
    val BuildGradleTemplate: FileTemplate
        get() = getOrCreateTemplate("build") { name ->
            templateManager.addTemplate(name, "gradle").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/build.gradle.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    private fun getOrCreateTemplate(name: String, create: (String) -> FileTemplate): FileTemplate {
        return templateManager.getTemplate(name) ?: create(name)
    }
}
