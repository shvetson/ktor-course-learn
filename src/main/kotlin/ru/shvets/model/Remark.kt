package ru.shvets.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 18:17
 */

@Serializable
data class Remark(
    val id: Long,
    val note: String,
)