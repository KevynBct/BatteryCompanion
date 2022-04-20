package companion.battery.ady.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import companion.battery.ady.extensions.isConnected

data class Device(
    val name: String,
    val address: String,
    val battery: Int,
    val isConnected: Boolean,
    val bluetoothClass: android.bluetooth.BluetoothClass
) {

    @SuppressLint("MissingPermission")
    constructor(bluetoothDevice: BluetoothDevice, batteryLevel: Int) : this(
        name = bluetoothDevice.name,
        address = bluetoothDevice.address,
        battery = batteryLevel,
        isConnected = bluetoothDevice.isConnected,
        bluetoothClass = bluetoothDevice.bluetoothClass
    )

    @SuppressLint("MissingPermission")
    constructor(bluetoothDevice: BluetoothDevice) : this(
        bluetoothDevice = bluetoothDevice,
        batteryLevel = -1
    )

}
