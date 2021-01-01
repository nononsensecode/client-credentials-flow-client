package com.nononsensecode.client.credential.flow.controller

import com.nononsensecode.client.credential.flow.dtos.AccessTokenDTO
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
class AccessTokenController {

    @GetMapping
    fun getAccessToken(): ResponseEntity<String> {
        val restTemplate = RestTemplate()
        val tokenUrl = "http://localhost:9000/auth/realms/heroes/protocol/openid-connect/token"

        val headers = HttpHeaders()
        headers.set("Content-Type", "application/x-www-form-urlencoded")

        val params = LinkedMultiValueMap<String, String>()
        params["client_secret"] = "a61c25c4-9ad0-49a8-8e16-011283cfa6dc"
        params["grant_type"] = "client_credentials"
        params["client_id"] = "hero-app-client"
        params["scope "] = "email heroes"

        val request = HttpEntity(params, headers)

        val accessToken = restTemplate.postForEntity(tokenUrl, request, AccessTokenDTO::class.java).body!!

        println(accessToken)

        val apiUrl = "http://localhost:10001/api/heroes"

        val apiHeaders = HttpHeaders()
        apiHeaders.set("Content-Type", "application/json")
        apiHeaders.set("authorization", "Bearer ${accessToken.token}")

        val apiRequest = HttpEntity(null, headers)

        return restTemplate.exchange(apiUrl, HttpMethod.GET, apiRequest, String::class.java)
    }
}

data class Hero(
    val id: Int,
    val name: String
)