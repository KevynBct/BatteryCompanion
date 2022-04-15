package companion.battery.ady.extensions

import android.bluetooth.BluetoothDevice

val BluetoothDevice.isConnected: Boolean
    get() = try { this.javaClass.getMethod("isConnected").invoke(this) as Boolean } catch (e: Exception) { false }