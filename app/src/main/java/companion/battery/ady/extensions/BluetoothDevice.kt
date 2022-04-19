package companion.battery.ady.extensions

import android.bluetooth.BluetoothDevice

val BluetoothDevice.isConnected: Boolean
    get() = try { this.javaClass.getMethod("isConnected").invoke(this) as Boolean } catch (e: Exception) { false }

val BluetoothDevice.batteryLevel: Int
    get() = try { this.javaClass.getMethod("getBatteryLevel").invoke(this) as Int } catch (e: Exception) { -1 }