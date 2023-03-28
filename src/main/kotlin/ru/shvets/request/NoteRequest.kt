package ru.shvets.request

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 19:29
 */

@Serializable
data class NoteRequest(
    val note: String,
)
