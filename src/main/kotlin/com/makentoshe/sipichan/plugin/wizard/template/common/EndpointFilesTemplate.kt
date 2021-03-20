package com.makentoshe.sipichan.plugin.wizard.template.common

import com.intellij.ide.fileTemplates.FileTemplate
import com.makentoshe.sipichan.plugin.wizard.template.FilesTemplate

object EndpointFilesTemplate : FilesTemplate() {

    private val prefix = "space-endpoint-file"

    /**
     * Contains attributes:
     * GROUP_ID
     * ARTIFACT_ID
     * ENDPOINT_VERIFICATION_TOKEN_PLACEHOLDER
     * ENDPOINT_SIGNING_KEY_PLACEHOLDER
     */
    val EndpointKtTemplate: FileTemplate
        get() = getOrCreateTemplate("Endpoint") { name ->
            templateManager.addTemplate("${prefix}-${name.toLowerCase()}", "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/common/$name.kt.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    /**
     * Contains attributes:
     * ENDPOINT_SIGNING_KEY
     */
    val EndpointSigningKeyTemplate: FileTemplate
        get() = getOrCreateTemplate("EndpointSigningKey") { name ->
            templateManager.addTemplate("${prefix}-${name.toLowerCase()}", "").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/common/$name.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    /**
     * Contains attributes:
     * ENDPOINT_VERIFICATION_TOKEN
     */
    val EndpointVerificationTokenTemplate: FileTemplate
        get() = getOrCreateTemplate("EndpointVerificationToken") { name ->
            templateManager.addTemplate("${prefix}-${name.toLowerCase()}", "").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/common/$name.ft")
                template.text = String(stream!!.readBytes())
            }
        }
}