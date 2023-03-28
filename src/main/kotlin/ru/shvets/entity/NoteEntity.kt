package ru.shvets.entity

import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 18:42
 */

object NotesEntity: Table<Nothing>("notes") {
    val id = long("id").primaryKey()
    val note = varchar("note")
}