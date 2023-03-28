package ru.shvets.model

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 17:02
 */

@Serializable
data class Person(
    val email: String,
    val password: String,
)
