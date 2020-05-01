package videodemos.example.marketplace

data class Listing(var title: String,
                   var cost: Int,
                   var description: String,
                   var traderId: Int,
                   var tags: MutableList<String>,
                   var imageId: Int)