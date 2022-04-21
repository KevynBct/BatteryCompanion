package companion.battery.ady.broadcasts

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import companion.battery.ady.model.Device

interface BluetoothBroadcastListener {
    fun onBroadcastReceive(device: Device)
}

class BluetoothBroadcastReceiver: BroadcastReceiver() {

    var listener: BluetoothBroadcastListener? = null

    companion object {

        private const val ACTION_BATTERY_LEVEL_CHANGED = "android.bluetooth.device.action.BATTERY_LEVEL_CHANGED"
        private const val EXTRA_BATTERY_LEVEL = "android.bluetooth.device.extra.BATTERY_LEVEL"

        val filters = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
            addAction(ACTION_BATTERY_LEVEL_CHANGED)
        }

    }

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {

            ACTION_BATTERY_LEVEL_CHANGED,
            BluetoothDevice.ACTION_ACL_CONNECTED,
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {

                val bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) as? BluetoothDevice ?: return
                val batteryLevel = intent.getIntExtra(EXTRA_BATTERY_LEVEL, -2)

                val device = Device(
                    bluetoothDevice = bluetoothDevice,
                    batteryLevel = batteryLevel
                )

                listener?.onBroadcastReceive(device = device)

            }

        }

    }

}