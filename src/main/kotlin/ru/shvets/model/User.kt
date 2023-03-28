package ru.shvets.model

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 23:15
 */

@Serializable
data class User(
    val id: Long,
    val username: String,
    val password: String,
)