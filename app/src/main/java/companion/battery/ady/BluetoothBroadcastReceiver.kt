package companion.battery.ady

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class BluetoothBroadcastReceiver: BroadcastReceiver() {

    val devices = mutableListOf<Device>()

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        val bluetoothDevice: BluetoothDevice = intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) ?: return

        val device = Device(
            name = bluetoothDevice.name,
            isConnected = false,
            macAddress = bluetoothDevice.address
        )

        when {
            BluetoothDevice.ACTION_FOUND == action -> {
                //Device found
            }
            BluetoothDevice.ACTION_ACL_CONNECTED == action -> {
                device.isConnected = true
                //Device is now connected
            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED == action -> {
                device.isConnected = false
                //Device has disconnected
            }
        }

        devices.removeIf { it.macAddress == device.macAddress }
        devices.add(device)

    }

    companion object {

        val BluetoothBroadcastFilter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        }

    }

}