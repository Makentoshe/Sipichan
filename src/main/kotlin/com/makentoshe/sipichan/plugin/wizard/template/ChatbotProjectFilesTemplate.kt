package com.makentoshe.sipichan.plugin.wizard.template

import com.intellij.ide.fileTemplates.FileTemplate

// TODO finish chatbot to be echoing bot
/** Object provides several file templates for the chatbot project building */
object ChatbotProjectFilesTemplate : FilesTemplate() {

    private val prefix = "Chatbot"

    /** src/Application.kt file template */
    val ApplicationKtTemplate: FileTemplate
        get() = getOrCreateTemplate("Application") { name ->
            templateManager.addTemplate("$prefix$name", "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/chatbot/$name.kt.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    /** src/CallContext.kt file template */
    val CallContextKtTemplate: FileTemplate
        get() = getOrCreateTemplate("CallContext") { name ->
            templateManager.addTemplate("$prefix$name", "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/chatbot/$name.kt.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    /** src/Client.kt file template */
    val ClientKtTemplate: FileTemplate
        get() = getOrCreateTemplate("Client") { name ->
            templateManager.addTemplate("$prefix$name", "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/chatbot/$name.kt.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    /** src/Commands.kt file template */
    val CommandsKtTemplate: FileTemplate
        get() = getOrCreateTemplate("Commands") { name ->
            templateManager.addTemplate("$prefix$name", "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/chatbot/$name.kt.ft")
                template.text = String(stream!!.readBytes())
            }
        }
}