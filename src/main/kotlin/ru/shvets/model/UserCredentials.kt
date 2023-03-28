package ru.shvets.model

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 21:35
 */

@Serializable
data class UserCredentials(
    val username: String,
    val password: String,
) {
    fun hashedPassword(): String{
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun isValidCredentials(): Boolean {
        return username.length >=3 && password.length >= 8
    }
}