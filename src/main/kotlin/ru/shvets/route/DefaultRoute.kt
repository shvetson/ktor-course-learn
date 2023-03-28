package ru.shvets.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.shvets.model.Person
import ru.shvets.model.UserResponse
import java.io.File

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 15:14
 */

fun Route.default() {

    route("") {
        get("/") {
            println("URI: ${call.request.uri}")
            println("Headers: ${call.request.headers.names()}")
            println("User-Agent: ${call.request.headers["User-Agent"]}")
            println("Accept: ${call.request.headers["Accept"]}")
            println("Query Parameters: ${call.request.queryParameters.names()}")
            println("Name: ${call.request.queryParameters["name"]}")
            println("Email: ${call.request.queryParameters["email"]}")

            call.respondText("Hello Ktor!")
        }

        get("/iphones/{page}") {
            val pageNumber = call.parameters["page"]
            call.respondText("Your are on Page number: $pageNumber")
        }

        post("/login"){
            val person = call.receive<Person>()
            println(person)
            call.respondText("Everything working")
        }

        get("/response") {
            val responseObject = UserResponse("Oleg", "shvetson@gmail.com")
            call.respond(responseObject)
        }

        get("/headers"){
            call.response.headers.append("server-name", "Ktor-server")
            call.response.headers.append("chocolate", "I love it")
            call.respondText("Headers Attached")
        }

        get("/fileDownload") {
            val file = File("files/photo.jpg")
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(
                    ContentDisposition.Parameters.FileName, "downloadableImage.jpg"
                ).toString()
            )
            call.respondFile(file)
        }

        get("/fileOpen") {
            val file = File("files/photo2.jpg")
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Inline.withParameter(
                    ContentDisposition.Parameters.FileName, "openImage.jpg"
                ).toString()
            )
            call.respondFile(file)
        }
    }
}
