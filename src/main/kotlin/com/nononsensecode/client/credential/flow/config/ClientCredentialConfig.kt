package com.nononsensecode.client.credential.flow.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties(prefix = "oauth2")
@PropertySource("classpath:client-secret.yaml", factory = YamlPropertySourceFactory::class)
class ClientCredentialConfig {
    lateinit var clientCredential: String
}