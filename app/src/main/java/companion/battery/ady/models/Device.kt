package companion.battery.ady.models

data class Device(
    val name: String,
    var isConnected: Boolean,
    val macAddress: String
)
