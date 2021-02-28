package com.makentoshe.sipichan.plugin.wizard

import com.makentoshe.sipichan.plugin.wizard.source.BlankProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.source.ChatbotProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.source.ProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.strategy.GradleSpaceModuleBuilderStrategy
import com.makentoshe.sipichan.plugin.wizard.strategy.SpaceModuleBuilderStrategy

/**
 * Class contains several settings defined in several steps.
 * This settings may be used in project initialization and templates inflating.
 */
class SpaceWizard(var projectType: ProjectType = ProjectType.BLANK) {

    var buildConfiguration = BuildConfiguration(
        BuildConfiguration.BuildSystem.GRADLE, "com.example", "untitled", "0.0.1"
    )
    var spaceInstance: SpaceInstanceUrl = SpaceInstanceUrl("")
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

    private fun strategy(properties: BuildConfiguration, provider: ProjectSourceProvider) =
        when (buildConfiguration.system) {
            BuildConfiguration.BuildSystem.GRADLE -> {
                GradleSpaceModuleBuilderStrategy(properties, provider)
            }
        }

    fun attributes() = buildConfiguration.attributes().let { attributes ->
        attributes.plus(clientCredentialsFlow.attributes()).plus(verificationTokenEndpoint.attributes())
            .plus(spaceInstance.attributes())
    }


    enum class ProjectType {
        BLANK, CHATBOT
    }
}

/**
 * Class contains any information about chosen build system for inflating the templates
 */
class BuildConfiguration(
    val system: BuildSystem,
    val group: String,
    val artifact: String,
    val version: String
) {

    fun attributes() = mapOf(
        "GROUP_ID" to group,
        "ARTIFACT_ID" to artifact,
        "VERSION" to version
    )

    enum class BuildSystem {
        GRADLE
    }
}

data class SpaceInstanceUrl(val instanceUrl: String) {

    private val templateInstanceUrl = if (instanceUrl.isBlank()) {
        "TODO(\"Place your Space instance url here\")"
    } else {
        "\"$instanceUrl\""
    }

    fun attributes() = mapOf("SPACE_INSTANCE_URL" to templateInstanceUrl)
}

/** One of available authentication mechanism in Space. They can be used together */
data class ClientCredentialsFlow(val clientId: String, val clientSecret: String) {

    private val templateClientId = if (clientId.isBlank()) {
        "TODO(\"Place your Space client id\")"
    } else {
        "\"$clientId\""
    }

    private val templateClientSecret = if (clientSecret.isBlank()) {
        "TODO(\"Place your Space client secret\")"
    } else {
        "\"$clientSecret\""
    }

    fun attributes() = mapOf(
        "CLIENT_CREDENTIALS_CLIENT_ID" to templateClientId,
        "CLIENT_CREDENTIALS_CLIENT_SECRET" to templateClientSecret
    )
}

/** One of available verification mechanism in Space. */
data class VerificationTokenEndpoint(val verificationToken: String) {

    private val templateVerificationToken = if (verificationToken.isBlank()) {
        "TODO(\"Place your Space verification token\")"
    } else {
        "\"$verificationToken\""
    }

    fun attributes() = mapOf(
        "ENDPOINT_VERIFICATION_TOKEN" to templateVerificationToken
    )
}
