package ru.shvets.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import ru.shvets.route.default
import ru.shvets.route.notesRoute

fun Application.configureRouting() {
    routing {
        default()
    }

    notesRoute()
}

