# Open Auth 2.0 - Client Credential Flow

This application is demonstrating `client credential flow` of OAuth 2.0. As authorization server, I used `Keycloak`.

Client id is `heroes-client` and there is a scope associated with the client and that is `heroes`.

You have to create a `client-secret.yaml` file in the resources directory. An example is there `client-secret.yaml.example`

You can find the resource server I used for this example [here](https://github.com/nononsensecode/spring-boot-oauth-resource-server)