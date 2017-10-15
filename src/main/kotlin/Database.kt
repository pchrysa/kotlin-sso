import org.litote.kmongo.*

val client = KMongo.createClient()
val database = client.getDatabase("users")
val collection = database.getCollection<User>()

class Database() {
    fun insert(user: User) {
        collection.insertOne(user)
    }

    fun get(id: String): User? {
        return collection.findOne("{_id: ${id.json}}")
    }

    fun getAll(): List<User> {
        val allMovies = collection.find()
        return allMovies.toList()
    }

    fun update(id: String, user: User) {
        collection.updateOneById(id!!, "{\$set:{name: ${user?.name?.json}, avatar: ${user?.avatar?.json}}}")
    }

    fun delete(id: String) {
        collection.deleteOne("{_id: ${id.json}}")
    }
}