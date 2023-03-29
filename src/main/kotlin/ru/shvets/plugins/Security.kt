package ru.shvets.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.application.*
import io.ktor.server.config.yaml.*
import ru.shvets.utils.TokenManager

fun Application.configureSecurity(config: YamlConfig) {
    val tokenManager = TokenManager(config)

    install(Authentication) {
        jwt {
            verifier(tokenManager.verifyJWTToken())
            realm = tokenManager.realmConfig
            validate { jwtCredential ->
                if (jwtCredential.payload.getClaim("username").asString().isNotEmpty()) {
                    JWTPrincipal(jwtCredential.payload)
                } else {
                    null
                }
            }
        }
    }

//    authentication {
//        jwt {
//            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
//            verifier(
//                JWT
//                    .require(Algorithm.HMAC256(config.secret))
//                    .withAudience(config.audience)
//                    .withIssuer(config.issuer)
//                    .build()
//            )
//            validate { credential ->
//                if (credential.payload.audience.contains(config.audience)) JWTPrincipal(credential.payload) else null
//            }
//        }
//    }
}
