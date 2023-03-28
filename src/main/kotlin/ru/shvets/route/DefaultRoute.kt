package ru.shvets.route

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 15:14
 */

fun Route.default() {

    route("") {
        get("/") {
            call.respondText("Hello Ktor!")
        }
    }
}
