package companion.battery.ady.broadcasts

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

interface BluetoothBroadcastListener {
    fun onBroadcastReceive(device: BluetoothDevice)
}

class BluetoothBroadcastReceiver: BroadcastReceiver() {

    var listener: BluetoothBroadcastListener? = null

    companion object {

        val filters = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        }

    }

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

        val bluetoothDevice: BluetoothDevice = intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) ?: return

        listener?.onBroadcastReceive(device = bluetoothDevice)

    }

}