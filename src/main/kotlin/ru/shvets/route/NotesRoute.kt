package ru.shvets.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*
import ru.shvets.db.DatabaseConnection
import ru.shvets.entity.NotesEntity
import ru.shvets.model.Note
import ru.shvets.model.Remark
import ru.shvets.request.NoteRequest
import ru.shvets.response.NoteResponse

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 19:06
 */

fun Application.notesRoute() {
    val db = DatabaseConnection.database

    routing {
        get("/notes") {
            val notes = db.from(NotesEntity).select()
                .map {
                    val id = it[NotesEntity.id]
                    val note = it[NotesEntity.note]
                    Remark(id ?: -1, note ?: "")
                }
            call.respond(notes)
        }

        post("/notes") {
            val request = call.receive<NoteRequest>()
            val result = db.insert(NotesEntity) {
                set(it.note, request.note)
            }

            if (result == 1) {
                // Send successfully response to the client
                call.respond(
                    HttpStatusCode.OK, NoteResponse(
                        success = true,
                        data = "Values has been successfully inserted"
                    )
                )
            } else {
                // Send failure response to the client
                call.respond(
                    HttpStatusCode.BadRequest, NoteResponse(
                        success = false,
                        data = "Failed to insert values"
                    )
                )
            }
        }

        get("/notes/{id}") {
            val id = call.parameters["id"]?.toLong() ?: -1

            val note = db.from(NotesEntity)
                .select()
                .where { NotesEntity.id eq id }
                .map {
                    val id = it[NotesEntity.id]!!
                    val note = it[NotesEntity.note]!!
                    Remark(
                        id = id,
                        note = note
                    )
                }.firstOrNull()

            if (note == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    NoteResponse(
                        success = false,
                        data = "Could not found note with the id = $id"
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    NoteResponse(
                        success = true,
                        data = note
                    )
                )
            }
        }

        put("/notes/{id}") {
            val id = call.parameters["id"]?.toLong() ?: -1
            val updatedNote = call.receive<NoteRequest>()

            val rowsEffected = db.update(NotesEntity) {
                set(it.note, updatedNote.note)
                where {
                    it.id eq id
                }
            }

            if (rowsEffected == 1) {
                call.respond(
                    HttpStatusCode.OK,
                    NoteResponse(
                        success = true,
                        data = "Note has been updated"
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(
                        success = false,
                        data = "Note failed to update"
                    )
                )
            }
        }

        delete("/notes/{id}") {
            val id = call.parameters["id"]?.toLong() ?: -1
            val rowsEffected = db.delete(NotesEntity){
                it.id eq id
            }

            if (rowsEffected == 1) {
                call.respond(
                    HttpStatusCode.OK,
                    NoteResponse(
                        success = true,
                        data = "Note has been deleted"
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(
                        success = false,
                        data = "Note failed to delete"
                    )
                )
            }
        }
    }
}