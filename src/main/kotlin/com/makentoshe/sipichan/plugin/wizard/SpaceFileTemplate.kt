package com.makentoshe.sipichan.plugin.wizard

import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

/** Object provides several file templates for project building */
object SpaceFileTemplate {

    private val templateManager = FileTemplateManager.getDefaultInstance()

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

    /** resources/logback.xml file template */
    val MainLogbackXmlTemplate: FileTemplate
        get() = getOrCreateTemplate("logback") { name ->
            templateManager.addTemplate(name, "xml").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/logback.xml")
                template.text = String(stream!!.readBytes())
            }
        }

    /** resources/application.conf file template */
    val MainApplicationConfTemplate: FileTemplate
        get() = getOrCreateTemplate("application") { name ->
            templateManager.addTemplate(name, "conf").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/application.conf.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    /** src/Application.kt file template */
    val MainApplicationKtTemplate: FileTemplate
        get() = getOrCreateTemplate("Application") { name ->
            templateManager.addTemplate(name, "kt").also { template ->
                val stream = javaClass.classLoader.getResourceAsStream("/templates/project/empty/Application.kt.ft")
                template.text = String(stream!!.readBytes())
            }
        }

    private fun getOrCreateTemplate(name: String, create: (String) -> FileTemplate): FileTemplate {
        return templateManager.getTemplate(name) ?: create(name)
    }

    class Factory(private val template: FileTemplate, private val attributes: Map<String, String>) {

        fun create(title: String, projectRoot: VirtualFile): VirtualFile {
            val file = try {
                createVirtualFile(projectRoot, title)
            } catch (exception: IOException) {
                throw ConfigurationException(exception.message)
            }
            VfsUtil.saveText(file, template.getText(attributes))
            return file
        }

        private fun createVirtualFile(parent: VirtualFile, title: String): VirtualFile {
            val file = createFile(parent.toNioPath(), title)

            // Todo specify message
            val virtualFile = VfsUtil.findFile(file, true)
                ?: throw ConfigurationException("VirtualFile($parent/$title) is null")

            // Todo specify message
            if (virtualFile.isDirectory) {
                throw ConfigurationException("VirtualFile($parent/$title) is directory")
            }

            VfsUtil.markDirtyAndRefresh(false, false, false, virtualFile)
            return virtualFile
        }

        private fun createFile(parent: Path, title: String) = parent.resolve(title).also { file ->
            Files.deleteIfExists(file)
            Files.createDirectories(parent)
            Files.createFile(file)
        }
    }
}
