package com.makentoshe.sipichan.plugin.wizard

import com.makentoshe.sipichan.plugin.wizard.source.BlankProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.source.ChatbotProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.source.ProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.strategy.GradleSpaceModuleBuilderStrategy
import com.makentoshe.sipichan.plugin.wizard.strategy.SpaceModuleBuilderStrategy

enum class ProjectType {
    BLANK, CHATBOT
}

sealed class BuildSystem {
    abstract val group: String
    abstract val artifact: String
    abstract val version: String

    data class Gradle(
        override val group: String,
        override val artifact: String,
        override val version: String
    ) : BuildSystem()
}

class SpaceWizard(
    var projectType: ProjectType = ProjectType.BLANK,
    var buildSystem: BuildSystem = BuildSystem.Gradle("com.example", "untitled", "0.0.1")
) {

    val buildConfiguration = BuildConfiguration(buildSystem)
    var clientCredentialsFlow: ClientCredentialsFlow = ClientCredentialsFlow("", "")
    var verificationTokenEndpoint: VerificationTokenEndpoint = VerificationTokenEndpoint("")

    fun strategy(): SpaceModuleBuilderStrategy {
        return when (projectType) {
            ProjectType.BLANK -> {
                strategy(buildConfiguration, BlankProjectSourceProvider(this))
            }
            ProjectType.CHATBOT -> {
                strategy(buildConfiguration, ChatbotProjectSourceProvider(this))
            }
        }
    }

    private fun strategy(properties: BuildConfiguration, provider: ProjectSourceProvider) = when (buildSystem) {
        is BuildSystem.Gradle -> {
            GradleSpaceModuleBuilderStrategy(properties, provider)
        }
    }

    fun attributes() = buildConfiguration.attributes().let { attributes ->
        attributes.plus(clientCredentialsFlow.attributes()).plus(verificationTokenEndpoint.attributes())
    }
}

class BuildConfiguration(val group: String, val artifact: String, val version: String) {

    constructor(buildSystem: BuildSystem) : this(buildSystem.group, buildSystem.artifact, buildSystem.version)

    fun attributes() = mapOf(
        "GRADLE_GROUP_ID" to group,
        "GRADLE_ARTIFACT_ID" to artifact,
        "GRADLE_VERSION" to version
    )
}

// TODO class SpaceInstance(instanceUrl: String)

class ClientCredentialsFlow(clientId: String, clientSecret: String) {

    private val clientId = if (clientId.isBlank()) {
        "TODO(\"Place your Space client id\")"
    } else {
        "\"$clientId\""
    }

    private val clientSecret = if (clientSecret.isBlank()) {
        "TODO(\"Place your Space client secret\")"
    } else {
        "\"$clientSecret\""
    }

    fun attributes() = mapOf(
        "CLIENT_CREDENTIALS_CLIENT_ID" to clientId,
        "CLIENT_CREDENTIALS_CLIENT_SECRET" to clientSecret
    )
}

class VerificationTokenEndpoint(verificationToken: String) {

    private val verificationToken = if (verificationToken.isBlank()) {
        "TODO(\"Place your Space client id\")"
    } else {
        "\"$verificationToken\""
    }

    fun attributes() = mapOf(
        "ENDPOINT_VERIFICATION_TOKEN" to verificationToken
    )
}
