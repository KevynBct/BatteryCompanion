package companion.battery.ady.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import companion.battery.ady.extensions.batteryLevel
import companion.battery.ady.extensions.isConnected

data class Device(
    val name: String,
    val address: String,
    val battery: Int,
    val status: DeviceStatus,
    val bluetoothClass: android.bluetooth.BluetoothClass
) {

    @SuppressLint("MissingPermission")
    constructor(bluetoothDevice: BluetoothDevice, batteryLevel: Int) : this(
        name = bluetoothDevice.name,
        address = bluetoothDevice.address,
        battery = batteryLevel,
        status = bluetoothDevice.isConnected,
        bluetoothClass = bluetoothDevice.bluetoothClass
    )

    @SuppressLint("MissingPermission")
    constructor(bluetoothDevice: BluetoothDevice) : this(
        bluetoothDevice = bluetoothDevice,
        batteryLevel = bluetoothDevice.batteryLevel
    )

    @SuppressLint("MissingPermission")
    constructor(bluetoothDevice: BluetoothDevice, status: DeviceStatus) : this(
        name = bluetoothDevice.name,
        address = bluetoothDevice.address,
        battery = bluetoothDevice.batteryLevel,
        status = status,
        bluetoothClass = bluetoothDevice.bluetoothClass
    )

}


enum class DeviceStatus {
    CONNECTED,
    CONNECTING,
    DISCONNECTED,
}