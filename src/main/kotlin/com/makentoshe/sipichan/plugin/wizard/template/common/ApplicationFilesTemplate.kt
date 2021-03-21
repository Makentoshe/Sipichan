package com.makentoshe.sipichan.plugin.wizard.template.common

import com.intellij.ide.fileTemplates.FileTemplate
import com.makentoshe.sipichan.plugin.wizard.template.FilesTemplate

object ApplicationFilesTemplate : FilesTemplate() {

    private val prefix = "space-application-file"

    /**
     * Contains attributes:
     * GROUP_ID
     * ARTIFACT_ID
     * APPLICATION_VERIFICATION_TOKEN_PLACEHOLDER
     * APPLICATION_SIGNING_KEY_PLACEHOLDER
     */
    val ApplicationKtTemplate: FileTemplate
        get() = getOrCreateTemplate("Application") { name ->
            templateManager.addTemplate("${prefix}-${name.toLowerCase()}", "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/common/$name.kt.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    val ApplicationVerificationTokenTemplate: FileTemplate
        get() = getOrCreateTemplate("ApplicationVerificationToken") { name ->
            templateManager.addTemplate("${prefix}-${name.toLowerCase()}", "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/common/$name.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    val ApplicationSigningKeyTemplate: FileTemplate
        get() = getOrCreateTemplate("ApplicationSigningKey") { name ->
            templateManager.addTemplate("${prefix}-${name.toLowerCase()}", "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/common/$name.ft")
                template.text = String(stream!!.readBytes())
            }
        }
}