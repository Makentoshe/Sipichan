package com.makentoshe.sipichan.plugin.wizard.template

import com.intellij.ide.fileTemplates.FileTemplate

/** Object provides gradle specific file templates for project configuring */
object GradleFilesTemplate: FilesTemplate() {

    /** build.gradle file template */
    val BuildGradleTemplate: FileTemplate
        get() = getOrCreateTemplate("build") { name ->
            templateManager.addTemplate(name, "gradle").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/gradle/build.gradle.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    /** settings.gradle file template */
    val SettingsGradleTemplate: FileTemplate
        get() = getOrCreateTemplate("settings") { name ->
            templateManager.addTemplate(name, "gradle").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/gradle/settings.gradle.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    /** gradle.properties file template */
    val GradlePropertiesTemplate: FileTemplate
        get() = getOrCreateTemplate("gradle") { name ->
            templateManager.addTemplate(name, "properties").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/gradle/gradle.properties.ft")
                template.text = String(stream!!.readBytes())
            }
        }
}