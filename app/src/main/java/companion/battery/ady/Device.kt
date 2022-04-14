package companion.battery.ady

data class Device(
    val name: String,
    var isConnected: Boolean,
    val macAddress: String
)
