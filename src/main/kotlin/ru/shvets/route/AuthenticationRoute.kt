package ru.shvets.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*
import ru.shvets.db.DatabaseConnection
import ru.shvets.entity.UserEntity
import ru.shvets.model.UserCredentials
import ru.shvets.response.NoteResponse

fun Application.authenticationRoute() {
    val db = DatabaseConnection.database

    routing {
        post("/register") {
            val userCredentials = call.receive<UserCredentials>()

            if (!userCredentials.isValidCredentials()) {
                call.respond(HttpStatusCode.BadRequest,
                NoteResponse(
                    success = false,
                    data = "Username should be greater than or equal 3 and password should be greater than or equal 8"
                ))
                return@post
            }

            val username = userCredentials.username
            val password = userCredentials.hashedPassword()

            // Check if username already exists
            val user = db.from(UserEntity)
                .select()
                .where { UserEntity.username eq username }
                .map { it[UserEntity.username] }
                .firstOrNull()

            if (user != null) {
                call.respond(
                    HttpStatusCode.Conflict,
                    NoteResponse(
                        success = false,
                        data = "User already exists, please try a different username"
                    )
                )
                return@post
            }

            db.insert(UserEntity) {
                set(it.username, username)
                set(it.password, password)
            }

            call.respond(
                HttpStatusCode.Created,
                NoteResponse(
                    success = true,
                    data = "User has been successfully created"
                )
            )
        }
    }
}