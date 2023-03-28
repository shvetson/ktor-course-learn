package ru.shvets.model

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 17:30
 */

@Serializable
data class UserResponse(
    val name: String,
    val email: String,
)
