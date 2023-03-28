package ru.shvets.model

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 18:17
 */

@Serializable
data class Note(
    val id: Long,
    val note: String,
)