import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.CallLogging
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.gson.GsonSupport
import org.jetbrains.ktor.request.receive
import org.jetbrains.ktor.response.respond
import org.jetbrains.ktor.routing.*

val db = Database()

fun Application.main() {

    install(GsonSupport)
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/api/users") {
            call.respond(ResponseList(db.getAll()))
        }

        get("/api/users/{id}") {
            val id = call.parameters["id"]
            call.respond(ResponseUser(db.get(id.toString())))
        }

        post("/api/users") {
            val user = call.receive<User>()
            db.insert(user)
            call.respond(ResponseMessage("$user added"))
        }

        put("/api/users/{id}") {
            val id = call.parameters["id"]
            val user = call.receive<User>()
            db.update(id.toString(), user)
            call.respond(ResponseMessage("$id updated"))
        }

        delete("/api/users/{id}") {
            val id = call.parameters["id"]
            db.delete(id.toString())
            call.respond(ResponseMessage("$id deleted"))
        }

    }
}