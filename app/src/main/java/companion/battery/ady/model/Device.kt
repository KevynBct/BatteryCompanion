package companion.battery.ady.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import companion.battery.ady.extensions.batteryLevel
import companion.battery.ady.extensions.isConnected
import kotlinx.parcelize.Parcelize

/**
 * Device with battery informations
 *
 * @param name Device's name
 * @param id Device's id (bluetooth MAC address or unique device's ID)
 * @param battery Battery level
 * @param isConnected Indicates if the device is connected or not
 * @param majorDeviceClass Device's bluetooth class (headphone, watch, phone...)
 */
@Parcelize
data class Device(
    val name: String,
    val id: String,
    val battery: Int,
    val isConnected: Boolean,
    val majorDeviceClass: Int
) : Parcelable {

    val batteryAvailable: Boolean
        get() = isConnected && battery in 0..100

    val batteryUnavailable: Boolean
        get() = isConnected && battery !in 0..100

    @SuppressLint("MissingPermission")
    constructor(bluetoothDevice: BluetoothDevice, batteryLevel: Int) : this(
        name = bluetoothDevice.name,
        id = bluetoothDevice.address,
        battery = batteryLevel,
        isConnected = bluetoothDevice.isConnected,
        majorDeviceClass = bluetoothDevice.bluetoothClass.majorDeviceClass
    )

    @SuppressLint("MissingPermission")
    constructor(bluetoothDevice: BluetoothDevice) : this(
        bluetoothDevice = bluetoothDevice,
        batteryLevel = bluetoothDevice.batteryLevel
    )

}