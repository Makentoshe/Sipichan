package com.makentoshe.sipichan.plugin.wizard.template

import com.intellij.ide.fileTemplates.FileTemplate

/** Object provides Space and Ktor specific file templates for project configuring */
object SpaceFilesTemplate: FilesTemplate() {

    /** resources/logback.xml file template */
    val LogbackXmlTemplate: FileTemplate
        get() = getOrCreateTemplate("logback") { name ->
            templateManager.addTemplate(name, "xml").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/logback.xml")
                template.text = String(stream!!.readBytes())
            }
        }

    /** resources/application.conf file template */
    val ApplicationConfTemplate: FileTemplate
        get() = getOrCreateTemplate("application") { name ->
            templateManager.addTemplate(name, "conf").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/application.conf.ft")
                template.text = String(stream!!.readBytes())
            }
        }
}