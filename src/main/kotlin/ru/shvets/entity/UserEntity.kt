package ru.shvets.entity

import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 21:26
 */

object UserEntity: Table<Nothing>("users") {
    val id = long("id").primaryKey()
    val username = varchar("username")
    val password = varchar("password")
}