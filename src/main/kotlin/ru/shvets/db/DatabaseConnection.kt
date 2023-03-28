package ru.shvets.db

import org.ktorm.database.Database

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 19:09
 */

object DatabaseConnection {

    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/ktor",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )
}