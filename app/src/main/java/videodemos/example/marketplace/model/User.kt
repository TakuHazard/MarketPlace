package videodemos.example.marketplace.model

class User(
    var uid: String,
    var username: String,
    var karma: Int,
    var profileImageUrl: String
) {
    constructor() : this("", "", 0, "")
}
