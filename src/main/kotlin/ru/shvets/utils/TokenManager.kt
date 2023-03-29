package ru.shvets.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.yaml.*
import ru.shvets.model.User
import java.util.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  29.03.2023 00:06
 */

class TokenManager(private val config: YamlConfig) {

    private val audience = config.property("jwt.audience").getString()
    private val secret = config.property("jwt.secret").getString()
    private val issuer = config.property("jwt.issuer").getString()
    private val realm = config.property("jwt.realm").getString()
    private val expiration = System.currentTimeMillis().plus(config.property("jwt.expiration").getString().toLong())

    fun generateJWTToken(user: User): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .withClaim("userId", user.id)
            .withExpiresAt(Date(expiration))
            .sign(Algorithm.HMAC256(secret))
    }

    fun verifyJWTToken(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
    }

    val realmConfig: String
        get() = realm
}