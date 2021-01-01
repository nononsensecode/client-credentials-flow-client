package com.nononsensecode.client.credential.flow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClientCredentialFlowApp

fun main(args: Array<String>) {
    runApplication<ClientCredentialFlowApp>(*args)
}