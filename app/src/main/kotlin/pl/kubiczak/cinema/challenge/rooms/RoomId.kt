package pl.kubiczak.cinema.challenge.rooms

@JvmInline
value class RoomId(val value: String) {
    init {
        require(value.length > 3) {}
    }
}
