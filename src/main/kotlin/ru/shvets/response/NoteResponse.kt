package ru.shvets.response

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 19:33
 */

@Serializable
data class NoteResponse<T>(
    val data: T,
    val success: Boolean
)
