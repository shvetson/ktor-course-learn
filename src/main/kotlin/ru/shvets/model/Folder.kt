package ru.shvets.model

import org.jetbrains.exposed.sql.Table

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 18:26
 */

data class Folder(
    val id: Long,
    val name: String,
)

object Folders: Table("folders") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 50)

    override val primaryKey = PrimaryKey(Notes.id)
}