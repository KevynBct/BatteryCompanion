package companion.battery.ady.extensions

import android.bluetooth.BluetoothDevice
import companion.battery.ady.model.DeviceStatus

val BluetoothDevice.isConnected: DeviceStatus
    get() = try {

        if (this.javaClass.getMethod("isConnected").invoke(this) as Boolean)
            DeviceStatus.CONNECTED
        else
            DeviceStatus.DISCONNECTED

    }
    catch (e: Exception) { DeviceStatus.DISCONNECTED }

val BluetoothDevice.batteryLevel: Int
    get() = try { this.javaClass.getMethod("getBatteryLevel").invoke(this) as Int } catch (e: Exception) { -1 }