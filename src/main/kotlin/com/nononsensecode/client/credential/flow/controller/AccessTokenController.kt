package com.nononsensecode.client.credential.flow.controller

import com.nononsensecode.client.credential.flow.config.ClientCredentialConfig
import com.nononsensecode.client.credential.flow.dtos.AccessTokenDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/api/v1.0/access-token")
class AccessTokenController(
    @Value("\${oauth2.token_url}")
    val tokenUrl: String,

    @Value("\${oauth2.grant_type}")
    val grantType: String,

    @Value("\${oauth2.client_id}")
    val clientId: String,

    @Value("\${oauth2.scope}")
    val scope: String,

    @Value("\${oauth2.resource_server.url}")
    val heroApiUrl: String,

    val clientCredentialsConfig: ClientCredentialConfig
) {

    @GetMapping
    fun getAccessToken(): ResponseEntity<List<Hero>> {
        val restTemplate = RestTemplate()

        val authHeader = HttpHeaders()
        authHeader.set("Content-Type", "application/x-www-form-urlencoded")

        val params = LinkedMultiValueMap<String, String>()
        params["client_id"] = clientId
        params["client_secret"] = clientCredentialsConfig.clientCredential
        params["scope"] = scope
        params["grant_type"] = grantType

        val authRequest = HttpEntity(params, authHeader)
        val authResponse = restTemplate.postForEntity(tokenUrl, authRequest, AccessTokenDTO::class.java)
        val accessToken = authResponse.body!!

        val heroHeader = HttpHeaders()
        heroHeader["content-type"] = "application/json"
        heroHeader["authorization"] = "Bearer ${accessToken.token}"

        val heroRequest = HttpEntity(null, heroHeader)

        return restTemplate.exchange(heroApiUrl, HttpMethod.GET, heroRequest, object : ParameterizedTypeReference<List<Hero>>() {})
    }
}

data class Hero(
    val id: Int,
    val name: String
)