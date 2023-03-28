package ru.shvets.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

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

object Notes: Table("notes") {
    val id = long("id").autoIncrement()
    val note = varchar("note", 1500)

    override val primaryKey = PrimaryKey(id)
}