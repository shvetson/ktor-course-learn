package ru.shvets.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 15:27
 */

fun Application.contactUsModule() {
    routing {
        get("/contactus") {
            call.respondText("Contact Us:")
        }

        get("/aboutus") {
            call.respondText("About Us:")
        }
    }
}