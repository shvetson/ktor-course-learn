package ru.shvets.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.yaml.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*
import org.mindrot.jbcrypt.BCrypt
import ru.shvets.db.DatabaseConnection
import ru.shvets.entity.UserEntity
import ru.shvets.model.User
import ru.shvets.model.UserCredentials
import ru.shvets.response.NoteResponse
import ru.shvets.utils.TokenManager

fun Application.authenticationRoute(config: YamlConfig) {
    val db = DatabaseConnection.database
    val tokenManager = TokenManager(config)

    routing {
        post("/register") {
            val userCredentials = call.receive<UserCredentials>()

            if (!userCredentials.isValidCredentials()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(
                        success = false,
                        data = "Username should be greater than or equal 3 and password should be greater than or equal 8"
                    )
                )
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

        post("/login") {
            val userCredentials = call.receive<UserCredentials>()

            val username = userCredentials.username
            val password = userCredentials.password

            // Check if user exists
            val user = db.from(UserEntity)
                .select()
                .where { UserEntity.username eq username }
                .map {
                    val id = it[UserEntity.id]!!
                    val username = it[UserEntity.username]!!
                    val password = it[UserEntity.password]!!
                    User(
                        id = id,
                        username = username,
                        password = password
                    )
                }.firstOrNull()

            if (user == null) {
                call.respond(
                    HttpStatusCode.Conflict,
                    NoteResponse(
                        success = false,
                        data = "Invalid username or password"
                    )
                )
                return@post
            }

            val doesPasswordMatch = BCrypt.checkpw(password, user.password)
            if (!doesPasswordMatch) {
                call.respond(
                    HttpStatusCode.Conflict,
                    NoteResponse(
                        success = false,
                        data = "Invalid username or password"
                    )
                )
                return@post
            }

            val token = tokenManager.generateJWTToken(user) // jwt

            call.respond(HttpStatusCode.OK,
            NoteResponse(
                success = true,
                data = token // jwt
//                data = "User successfully logged in"
            ))
        }

        authenticate {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()
                println(principal?.payload?.claims?.toString())

                if (principal != null) {
                    val username = principal.payload.getClaim("username").asString()
                    val userId = principal.payload.getClaim("userId").asLong()
                    val exp = principal.payload.getClaim("exp").asDate()
                    call.respondText("Hello, $username with id $userId, expire - $exp")
                }
            }
        }
    }
}