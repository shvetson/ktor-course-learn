package ru.shvets

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.ktorm.database.Database
import org.ktorm.dsl.insert
import ru.shvets.dao.DatabaseFactory
import ru.shvets.entity.NotesEntity
import ru.shvets.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
//    DatabaseFactory.init()

    configureSerialization()
    configureRouting()
    contactUsModule()

    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/ktor",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )

    database.insert(NotesEntity){
        set(it.note, "Wash Clothes")
    }
    database.insert(NotesEntity){
        set(it.note, "Buy Groceries")
    }
    database.insert(NotesEntity){
        set(it.note, "Workout")
    }
}


//fun main() {
//
//    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
//        configureRouting()
//    }.start(wait = true)
//}

