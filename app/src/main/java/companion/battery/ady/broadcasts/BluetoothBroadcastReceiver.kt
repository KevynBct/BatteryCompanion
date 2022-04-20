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

        val filters = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
            addAction("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED")
        }

    }

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

        val bluetoothDevice: BluetoothDevice = intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) ?: return

        val batteryLevel = intent.getIntExtra("android.bluetooth.device.extra.BATTERY_LEVEL", -2)

        val device = Device(
            bluetoothDevice = bluetoothDevice,
            batteryLevel = batteryLevel
        )

        listener?.onBroadcastReceive(device = device)

    }

}