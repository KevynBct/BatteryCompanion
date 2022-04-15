package companion.battery.ady.extensions

import android.bluetooth.BluetoothDevice
import android.util.Log

val BluetoothDevice.isConnected: Boolean
    get() = try {
        val method = this.javaClass.getMethod("isConnected")
        method.invoke(this) as Boolean
    } catch (e: Exception) {
        Log.i("TEST_", "error : $e")
        false
    }