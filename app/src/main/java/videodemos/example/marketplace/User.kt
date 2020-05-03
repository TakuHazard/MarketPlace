package videodemos.example.marketplace

class User(
    val uid: String,
    val username: String,
    val karma: Int,
    val profileImageUrl: String
) {
    constructor() : this("", "", 0, "")
}
